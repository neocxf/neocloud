package top.neospot.cloud.inventory;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
public class InventoryController {
    private final InventoryItemRepository inventoryItemRepository;
    private final CatalogClient catalogClient;
    private AtomicReference<String> applications = new AtomicReference<>("aaaa");
 
    @Autowired
    public InventoryController(InventoryItemRepository inventoryItemRepository, CatalogClient catalogClient) {
        this.inventoryItemRepository = inventoryItemRepository;
        this.catalogClient = catalogClient;
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Random random = new Random(System.currentTimeMillis());
                String val = "aaa" + random.nextInt(10);
                applications.set(val);
            }
        }, 5, 5, TimeUnit.SECONDS);
    }
 

    @GetMapping("/api/inventory/{productCode}")
    @HystrixCommand
    public ResponseEntity<InventoryItem> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
        log.info("Finding inventory for product code :"+productCode);
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findByProductCode(productCode);
        return inventoryItem.map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/inventory")
    @HystrixCommand
    public Map<String, Object> getInventory() {
        log.info("Finding inventory for all products ");
        Map<String, Object> res = new HashMap<>();
        res.put("inventories", inventoryItemRepository.findAll());
        res.put("products", catalogClient.catalogList());
        res.put("val", applications.get());
        return res;
    }
}
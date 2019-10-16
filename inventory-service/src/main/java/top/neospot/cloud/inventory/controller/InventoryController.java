package top.neospot.cloud.inventory.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.inventory.entity.InventoryItem;
import top.neospot.cloud.inventory.model.ProductInventoryDemand;
import top.neospot.cloud.inventory.request.ProductInventoryUpdateRequest;
import top.neospot.cloud.inventory.service.CacheService;
import top.neospot.cloud.inventory.service.InventoryItemService;
import top.neospot.cloud.inventory.thread.RequestProcessorThreadPool;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@RestController
@Slf4j
public class InventoryController {
    private volatile AtomicLong id = new AtomicLong(0);

    @Autowired
    CuratorFramework curatorFramework;

    @Autowired
    private InventoryItemService inventoryItemService;

    @Autowired
    private CacheService cacheService;

    private ReentrantReadWriteLock.WriteLock lock = new ReentrantReadWriteLock().writeLock();


    /*
    100 thread, run 10 cycles, throughput: 50+/s
   */
    @PostMapping("/minusInventoryByOptLock")
    public ResponseEntity<ProductInventoryDemand> minusInventoryByOptLock(@RequestBody ProductInventoryDemand productInventoryDemand, HttpServletRequest servletRequest) throws Exception {

        log.info("minusInventory: {}", productInventoryDemand);

        try {
            inventoryItemService.minusInventory(productInventoryDemand);

        } catch (Exception ex) {
            // do nothing
        } finally {
        }

        return ResponseEntity.ok(productInventoryDemand);
    }

    /*
    分段lock增加throughput minusInventoryByZKLockConcurrent

    100 thread, run 10 cycles, throughput: 20/s
     */
    @PostMapping("/minusInventoryByZKLockConcurrent")
    public ResponseEntity<ProductInventoryDemand> minusInventoryByZKLockConcurrent(@RequestBody ProductInventoryDemand productInventoryDemand, HttpServletRequest servletRequest) throws Exception {
        InterProcessSemaphoreMutex interProcessSemaphoreMutex = null;

        Integer index = cacheService.lockInventoryItem(productInventoryDemand.getProductCode(), productInventoryDemand.getProductCnt());
        String lockPath = String.format("/inventory-cnt-%s-%d", productInventoryDemand.getProductCode(), index);

        try {
            interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework, lockPath);

            interProcessSemaphoreMutex.acquire();

            log.info("minusInventory: {}", productInventoryDemand);

            inventoryItemService.minusInventory(productInventoryDemand);

        } finally {
            assert interProcessSemaphoreMutex != null;

            interProcessSemaphoreMutex.release();

            boolean unlockSuccess = cacheService.unlockInventoryItem(productInventoryDemand.getProductCode(), index);

            if (unlockSuccess) {
                log.info("unlock success for: {} ", lockPath);
            } else {
                log.info("unlock failed for: {} ", lockPath);
            }
        }

        return ResponseEntity.ok(productInventoryDemand);
    }

    /*
    minusInventoryByZKLock
   100 thread, run 10 cycles, throughput: 20/s
    */
    @PostMapping("/minusInventoryByZKLock")
    public ResponseEntity<ProductInventoryDemand> minusInventoryByZKLock(@RequestBody ProductInventoryDemand
                                                                             productInventoryDemand, HttpServletRequest servletRequest) throws Exception {

        InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework, "/inventory-cnt-" + productInventoryDemand.getProductCode());
        interProcessSemaphoreMutex.acquire();

        log.info("minusInventory: {}", productInventoryDemand);

        inventoryItemService.minusInventory(productInventoryDemand);

        interProcessSemaphoreMutex.release();

        return ResponseEntity.ok(productInventoryDemand);
    }

    /*
    100 thread, run 10 cycles, throughput: 20/s
     */
    @PostMapping("/minusInventoryByQueue")
    public void minusInventoryByQueue(@RequestBody ProductInventoryDemand productInventoryDemand, HttpServletRequest
        servletRequest) throws Exception {
//        InterProcessSemaphoreMutex interProcessSemaphoreMutex = new InterProcessSemaphoreMutex(curatorFramework, "/inventory-cnt-" + productInventory.getProductCode());
//        interProcessSemaphoreMutex.acquire();

        RequestProcessorThreadPool processorThreadPool = (RequestProcessorThreadPool) servletRequest.getServletContext().getAttribute(RequestProcessorThreadPool.class.getName());
        processorThreadPool.dispatch(new ProductInventoryUpdateRequest(inventoryItemService, productInventoryDemand));
        id.incrementAndGet();
        System.out.println("recv-request:" + id.longValue());

//        interProcessSemaphoreMutex.release();
    }

    @PostMapping("/updateInventorySync")
    public void updateInventorySync(@RequestBody ProductInventoryDemand productInventoryDemand, HttpServletRequest
        servletRequest) {

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/getProductInfo")
    public ProductInfo getProductInfo(long productId) {
        return ProductInfo.builder().id(productId).color("red").name("product" + productId).price(productId * 10).service("service" + productId).size(String.valueOf(productId * 0.5)).build();
    }

    @GetMapping("/getShopInfo")
    public ShopInfo getShopInfo(long shopId) {
        return ShopInfo.builder().id(shopId).name("shop" + shopId).level(String.valueOf(shopId)).comment("comment" + shopId).build();
    }


    @GetMapping("/api/inventory/{productCode}")
    @HystrixCommand
    public ResponseEntity<InventoryItem> findInventoryByProductCode(@PathVariable("productCode") String productCode) {
        log.info("Finding inventory for product code :" + productCode);
        InventoryItem inventoryItem = inventoryItemService.getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, productCode));

        return Optional.ofNullable(inventoryItem).map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/api/inventory")
    @HystrixCommand
    public Map<String, Object> getInventory() {
        log.info("Finding inventory for all products ");
        Map<String, Object> res = new HashMap<>();
        res.put("inventories", inventoryItemService.list(null));

        return res;
    }
}

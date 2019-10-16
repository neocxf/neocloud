package top.neospot.cloud.inventory.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.neospot.cloud.inventory.entity.InventoryItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CacheService implements InitializingBean {
    private ConcurrentHashMap<String, InventoryItem> productInventoryCache = new ConcurrentHashMap<>();

    @Autowired
    private InventoryItemService inventoryItemService;

    @Override
    public void afterPropertiesSet() throws Exception {
        loadFromDb();
    }

//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void loadFromDb() {
        List<InventoryItem> list = inventoryItemService.list();

        list.forEach(inventoryItem -> {
            productInventoryCache.put(inventoryItem.getProductCode(), inventoryItem);
        });
    }

    public InventoryItem getInventoryItem(String productCode) {
        return productInventoryCache.get(productCode);
    }

    public InventoryItem updateInventoryItem(InventoryItem inventoryItem) {
        return productInventoryCache.put(inventoryItem.getProductCode(), inventoryItem);
    }
}

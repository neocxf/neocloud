package top.neospot.cloud.inventory.service;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import top.neospot.cloud.inventory.entity.InventoryItem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CacheService implements InitializingBean {
    private static final int SLOT_UNIT = 50;
    private ConcurrentHashMap<String, List<SlotItem>> productInventoryCache = new ConcurrentHashMap<>();

    @Autowired
    private InventoryItemService inventoryItemService;

    @Override
    public void afterPropertiesSet() throws Exception {
        syncFromDb();
    }

    private void trySplitAndReloadCache(InventoryItem inventoryItem) {
        int total = inventoryItem.getQuantity();

        List<SlotItem> slotItems = new ArrayList<>();
        List<Integer> split = split(total, 50);

        for (int i = 0; i < split.size(); i++) {
            int slot = split.get(i);
            SlotItem slotItem = new SlotItem();
            slotItem.index = i;
            slotItem.current = new AtomicInteger(slot);
            slotItem.origin = slot;
            slotItem.inventoryId = inventoryItem.getId();
            slotItem.unit = SLOT_UNIT;
            slotItems.add(slotItem);
        }

        List<SlotItem> items = productInventoryCache.get(inventoryItem.getProductCode());

        if (items != null) {
            int totalRemaining = items.stream().map(SlotItem::actualPayable).reduce(0, Integer::sum);
            if (totalRemaining < total)
                productInventoryCache.put(inventoryItem.getProductCode(), slotItems);
            return;
        }

        productInventoryCache.putIfAbsent(inventoryItem.getProductCode(), slotItems);
    }

    @Scheduled(cron = "0 0/3 * * * ? ")
    void syncFromDb() {
        List<InventoryItem> inventoryItems = inventoryItemService.list();
        inventoryItems.forEach(this::trySplitAndReloadCache);
    }

    private static List<Integer> split(int total, int unit) {
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < total / unit; i++) {
            result.add(unit);
        }

        if (total % unit != 0) {
            result.add(total % unit);
        }

        return result;
    }

    /**
     * return the index of partition of the specific product
     *
     * @param productCode product code
     * @return the sequence
     */
    public Integer lockInventoryItem(String productCode, int cost) {
        List<SlotItem> slotItems = productInventoryCache.get(productCode);

        if (slotItems == null) {
            throw new RuntimeException("illegal product code");
        }

        Optional<SlotItem> maxOptional = slotItems.stream().filter(slotItem -> slotItem.canAfford(cost)).max(Comparator.comparingInt(SlotItem::actualPayable));

        if (maxOptional.isPresent()) {
            SlotItem slotItem = maxOptional.get();
            slotItem.setPaying(cost);
            return slotItem.index;
        }

        long inventoryId = slotItems.get(0).inventoryId;
        int total = slotItems.stream().map(SlotItem::getCurrent).reduce(new AtomicInteger(0), (x, y) -> new AtomicInteger(x.get() + y.get())).get();

        if (total < cost) {
            throw new RuntimeException("insufficient product balance for cost: " + cost);
        }

        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setProductCode(productCode);
        inventoryItem.setQuantity(total);
        inventoryItem.setId(inventoryId);

        trySplitAndReloadCache(inventoryItem);

        return lockInventoryItem(productCode, cost);
    }

    public boolean unlockInventoryItem(String productCode, int idx) {
        List<SlotItem> slotItems = productInventoryCache.get(productCode);

        Optional<SlotItem> lockOptional = slotItems.stream().filter(slotItem -> slotItem.index == idx).findFirst();

        boolean paySuccess = false;

        if (lockOptional.isPresent()) {
            SlotItem slotItem = lockOptional.get();
            paySuccess = slotItem.pay();
            slotItem.setPaying(0);
        }

        return paySuccess;
    }

    @Data
    private static class SlotItem {
        private int index;
        private long inventoryId;
        private volatile AtomicInteger current; // only this field is constant changing
        private int origin;
        private int total;
        private int unit;
        private volatile int paying; // the cost that has not be done

        boolean canAfford(int cost) {
            return actualPayable() >= cost;
        }

        int actualPayable() {
            return (current.get() - paying);
        }

        boolean pay() {
            int currentVal = current.get();
            if (currentVal == 0) return false;
            if (paying > currentVal) return false;
            return current.compareAndSet(currentVal, currentVal - paying);
        }
    }
}

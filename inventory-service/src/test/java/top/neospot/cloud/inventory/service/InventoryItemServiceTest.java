package top.neospot.cloud.inventory.service;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import top.neospot.cloud.inventory.InventoryApplicationTest;
import top.neospot.cloud.inventory.entity.InventoryItem;

@Rollback
public class InventoryItemServiceTest extends InventoryApplicationTest {

    @Autowired
    InventoryItemService inventoryItemService;

    @Test
    public void testInsert() {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setQuantity(11);
        inventoryItem.setProductCode("p4");
        inventoryItemService.save(inventoryItem);

    }

    @Test
    public void testSelect() {
        InventoryItem p1 = inventoryItemService.getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, "p1"));
        System.out.println(p1);
    }

    @Test
    public void testUpdate() {
        InventoryItem p1 = inventoryItemService.getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, "p3"));

        p1.setQuantity(p1.getQuantity() - 1);
        inventoryItemService.getBaseMapper().updateById(p1);

    }

    @Test
    public void testUpdateInventory() {
        InventoryItem p1 = inventoryItemService.getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, "p1"));

        p1.setQuantity(p1.getQuantity() - 1);

        inventoryItemService.update(p1, Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, p1.getProductCode()).gt(InventoryItem::getQuantity, 0));

    }

    @Test
    public void testUpdateInventoryError() {
        InventoryItem p1 = inventoryItemService.getOne(Wrappers.<InventoryItem>lambdaQuery().eq(InventoryItem::getProductCode, "p1"));

        /**
         * TODO: the lastUpdateTime not updated
         */
        inventoryItemService.update(Wrappers.<InventoryItem>lambdaUpdate()
            .ge(InventoryItem::getQuantity, 0)
            .eq(InventoryItem::getProductCode, "p2")
            .eq(InventoryItem::getVersion, p1.getVersion())
            .set(InventoryItem::getVersion, p1.getVersion() + 1)
            .set(InventoryItem::getQuantity, p1.getQuantity() - 1));
    }

}

package top.neospot.cloud.inventory.request;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import top.neospot.cloud.common.exceptions.CloudBizException;
import top.neospot.cloud.inventory.entity.InventoryItem;
import top.neospot.cloud.inventory.model.ProductInventory;
import top.neospot.cloud.inventory.service.InventoryItemService;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Slf4j
@ToString
public class ProductInventoryUpdateRequest extends Request {
    private ProductInventory productInventory;
    private InventoryItemService inventoryItemService;

    public ProductInventoryUpdateRequest(InventoryItemService inventoryItemService, ProductInventory productInventory) {
        super(productInventory.getProductCode());
        this.productInventory = productInventory;
        this.inventoryItemService = inventoryItemService;
    }

    @Override
    public void process() {
        // delete redis cache
        log.debug(String.format("delete cache of %s", productInventory));

        // update db
        log.debug(String.format("update db of %s", productInventory));

        try {
            inventoryItemService.minusInventory(productInventory);

        } catch (Exception ex) {
            // do nothing
        }




    }


}

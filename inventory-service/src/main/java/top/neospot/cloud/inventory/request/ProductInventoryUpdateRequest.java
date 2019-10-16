package top.neospot.cloud.inventory.request;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import top.neospot.cloud.inventory.model.ProductInventoryDemand;
import top.neospot.cloud.inventory.service.InventoryItemService;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Slf4j
@ToString
public class ProductInventoryUpdateRequest extends Request {
    private ProductInventoryDemand productInventoryDemand;
    private InventoryItemService inventoryItemService;

    public ProductInventoryUpdateRequest(InventoryItemService inventoryItemService, ProductInventoryDemand productInventoryDemand) {
        super(productInventoryDemand.getProductCode());
        this.productInventoryDemand = productInventoryDemand;
        this.inventoryItemService = inventoryItemService;
    }

    @Override
    public void process() {
        // delete redis cache
        log.debug(String.format("delete cache of %s", productInventoryDemand));

        // update db
        log.debug(String.format("update db of %s", productInventoryDemand));

        try {
            inventoryItemService.minusInventory(productInventoryDemand);

        } catch (Exception ex) {
            // do nothing
        }


    }


}

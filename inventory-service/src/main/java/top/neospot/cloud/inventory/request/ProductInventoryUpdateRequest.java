package top.neospot.cloud.inventory.request;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import top.neospot.cloud.inventory.model.ProductInventory;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Slf4j
@ToString
public class ProductInventoryUpdateRequest extends Request {
    private ProductInventory productInventory;

    public ProductInventoryUpdateRequest(ProductInventory productInventory) {
        super(productInventory.getProductId());
        this.productInventory = productInventory;
    }

    @Override
    public void process() {
        // delete redis cache
        log.debug(String.format("delete cache of %s", productInventory));

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // update db
        log.debug(String.format("update db of %s", productInventory));
    }


}

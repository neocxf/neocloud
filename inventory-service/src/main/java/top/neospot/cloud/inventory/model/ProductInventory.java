package top.neospot.cloud.inventory.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Data
public class ProductInventory {
    private long productId;
    private String productCode;
    private int productCnt;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

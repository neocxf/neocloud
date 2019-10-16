package top.neospot.cloud.inventory.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import top.neospot.cloud.common.model.BaseModel;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/10/16.
 */
@TableName("inventory_request_history")
@Data
public class ProductInventoryHistory extends BaseModel {
    private long userId;
    private long productId;
    private String productCode;
    private int quantity;
}

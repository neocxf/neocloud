package top.neospot.cloud.inventory.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import top.neospot.cloud.common.model.BaseModel;

@Data
public class InventoryItem extends BaseModel {
    private String productCode;
    private Integer quantity = 0;

    @Version
    private Integer version;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
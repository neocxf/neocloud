package top.neospot.cloud.shipping.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.neospot.cloud.common.model.BaseModel;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("shipping_order")
public class ShippingOrder extends BaseModel {
    private Short type;
    private Long orderId;
    private Long rewardExchangeId;
    private Long  number;
    private String addr;
    private Boolean shipped;
    private Boolean orderConfirmed;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

package top.neospot.cloud.order.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order extends UserBasedModel {
    
    private static final long serialVersionUID = 661434701950670670L;
    
    private Long orderId;
    
    private Long userId;
    
    private Long addressId;
    
    private String status;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
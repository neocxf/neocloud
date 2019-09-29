package top.neospot.cloud.order.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class OrderItem extends UserBasedModel {
    
    private static final long serialVersionUID = 263434701950670170L;
    
    private Long orderItemId;
    
    private Long orderId;
    
    private Long userId;
    
    private String status;

    private String name;

    private Double price;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
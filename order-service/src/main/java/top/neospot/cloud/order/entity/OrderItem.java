package top.neospot.cloud.order.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItem extends UserBasedModel {
    
    private static final long serialVersionUID = 263434701950670170L;

    @TableId
    private Long orderItemId;
    
    private Long orderId;

    private Long productId;

    private String productName;

    private int num;

    private Double costPerUnit;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
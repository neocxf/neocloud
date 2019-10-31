package top.neospot.cloud.order.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Order extends UserBasedModel {
    
    private static final long serialVersionUID = 661434701950670670L;

    @TableId
    private Long orderId;

    private Long addressId;

    private Long credit;

    private String lastMessageId;

    /**
     * 1. 未支付
     * 2. 已支付
     * 3. 已发货
     */
    private String status;

    /**
     * save the deduct logic for backup
     */
    private String field1;

    @TableField
    private transient List<OrderItem> items;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }

    public Double totalCost() {
        return items.stream().map(item -> item.getCostPerUnit() * item.getNum()).reduce(0.0, Double::sum);
    }
}
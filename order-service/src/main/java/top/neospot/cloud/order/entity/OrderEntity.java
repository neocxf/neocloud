package top.neospot.cloud.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.neospot.cloud.common.model.BaseModel;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("t_order")
public class OrderEntity extends BaseModel {

    private Long productId;

    private Long number;

    private Double price;

    private Long userId;

    private Long credit;

    private String lastMessageId;

    /**
     * 1. 未支付
     * 2. 已支付
     * 3. 已发货
     */
    private String status;
}

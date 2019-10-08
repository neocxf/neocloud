package top.neospot.cloud.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
public interface OrderMapper extends BaseMapper<Order> {
    Order findOrderById(Long id);

    OrderItem findOrderItemById(Long id);
}

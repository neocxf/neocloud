package top.neospot.cloud.order.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;
import top.neospot.cloud.order.mapper.OrderMapper;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/30.
 */
public interface OrderService extends IService<Order> {

    boolean removeOrders(Wrapper<Order> wrapper);

    boolean batchInsertOrderLines(List<OrderItem> orderItems);

    Order showOrder(Long orderId);


}

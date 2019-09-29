package top.neospot.cloud.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/27.
 */
public class OrderMapperTest extends OrderAppTest {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Before
    public void init() {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderItem::getOrderId, 385115156948430849L)   ;

        OrderItem order = orderItemMapper.selectOne(queryWrapper);

        System.out.println("---------------------------------------");

        if (order == null) {
            order = new OrderItem();
            order.setStatus("未支付");
            order.setStatus("item1");
            order.setName("washer");
            order.setPrice(20.5);
            order.setUserId(10L);
            order.setOrderId(385115156948430849L);
            orderItemMapper.insert(order);
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Order> orderList = orderMapper.selectList(null);
        orderList.forEach(System.out::println);
    }


    @Test
    public void testSelectOrderItems() {
        System.out.println(("----- selectAll method test ------"));
        List<OrderItem> orderList = orderItemMapper.selectList(null);
        orderList.forEach(System.out::println);
    }

    @Test
    public void testSelectOrderAndOrderItems() {
        System.out.println(("----- selectAll method test ------"));

        List<Order> orders = orderMapper.selectList(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, 10));
        orders.forEach(order -> {
            List<OrderItem> orderItems = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, order.getOrderId()));
            System.out.println(orderItems);
        });

    }
}

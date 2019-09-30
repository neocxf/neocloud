package top.neospot.cloud.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.OrderItem;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/27.
 */
public class OrderItemMapperTest extends OrderAppTest {

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Before
    public void init() {
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderItem::getOrderId, 385455987790163969L)   ;
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);
        OrderItem order = null;

        System.out.println("---------------------------------------");

        if (orderItems.size() == 0) {
            order = new OrderItem();
            order.setStatus("未支付");
            order.setStatus("item2");
            order.setName("food");
            order.setPrice(15.0);
            order.setUserId(10L);
            order.setOrderId(385455987790163969L);
            orderItemMapper.insert(order);
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<OrderItem> orderList = orderItemMapper.selectList(null);
        orderList.forEach(System.out::println);
    }

    @Test
    public void testInsert() {
        OrderItem order = null;

        System.out.println("---------------------------------------");

        order = new OrderItem();
        order.setStatus("未支付");
        order.setStatus("item2");
        order.setName("food");
        order.setPrice(15.0);
        order.setUserId(10L);
        order.setOrderId(385455987790163969L);
        orderItemMapper.insert(order);
    }


    @Test
    public void testSelectOrderItems() {
        System.out.println(("----- selectAll method test ------"));
        List<OrderItem> orderList = orderItemMapper.selectList(null);
        orderList.forEach((System.out::println));
    }

    @Test
    public void testSelectOrderAndOrderItems() {
        System.out.println(("----- selectAll method test ------"));

//        List<Order> orders = orderMapper.selectList(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, 10));
//        orders.forEach(order -> {
//            List<OrderItem> orderItems = orderItemMapper.selectList(Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderId, order.getOrderId()));
//            System.out.println(orderItems);
//        });

    }
}

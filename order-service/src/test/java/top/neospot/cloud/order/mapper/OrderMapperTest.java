package top.neospot.cloud.order.mapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderItem;

import java.util.Arrays;
import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/27.
 */
@Rollback(false)
public class OrderMapperTest extends OrderAppTest {

    @Autowired
    private OrderMapper orderMapper;

    @Before
    public void init() {
//        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
//        queryWrapper.lambda().eq(Order::getUserId, 13L);
//
//        Order order = orderMapper.selectOne(queryWrapper);
//
//        System.out.println("---------------------------------------");
//
//        if (order == null) {
//            order = new Order();
//            order.setStatus("未支付");
//            order.setStatus("item2");
//            order.setUserId(13L);
//            orderMapper.insert(order);
//        }

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
        Order order = orderMapper.findOrderById(385455987790163969L);
        order.getItems().forEach(System.out::println);
    }


    @Test
    public void testInsertOrderItems() {
        OrderItem o1 =  new OrderItem().setNum(1).setCostPerUnit(1.5).setProductId(1L).setOrderId(1L).setProductName("pro_1").setOrderId(1L);
        OrderItem o2 =  new OrderItem().setNum(2).setCostPerUnit(2.5).setProductId(2L).setOrderId(1L).setProductName("pro_2").setOrderId(2L);
        OrderItem o3 =  new OrderItem().setNum(3).setCostPerUnit(3.0).setProductId(3L).setOrderId(1L).setProductName("pro_3").setOrderId(3L);
        OrderItem o4 =  new OrderItem().setNum(4).setCostPerUnit(4.0).setProductId(4L).setOrderId(1L).setProductName("pro_4").setOrderId(4L);

        o1.setUserId(1L);
        o2.setUserId(1L);
        o3.setUserId(1L);
        o4.setUserId(1L);

        orderMapper.batchInsertOrderItems(Arrays.asList(o1,o2,o3,o4));
    }
}


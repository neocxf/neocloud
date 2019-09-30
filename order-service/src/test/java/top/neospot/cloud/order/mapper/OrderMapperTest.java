package top.neospot.cloud.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.Order;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/27.
 */
public class OrderMapperTest extends OrderAppTest {

    @Autowired
    private OrderMapper orderMapper;

    @Before
    public void init() {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Order::getUserId, 13L);

        Order order = orderMapper.selectOne(queryWrapper);

        System.out.println("---------------------------------------");

        if (order == null) {
            order = new Order();
            order.setStatus("未支付");
            order.setStatus("item2");
            order.setUserId(13L);
            orderMapper.insert(order);
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
        Order order = orderMapper.findOrderById(385455987790163969L);
        order.getItems().forEach(System.out::println);
    }
}


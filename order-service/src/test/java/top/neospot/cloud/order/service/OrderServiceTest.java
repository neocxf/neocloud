package top.neospot.cloud.order.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.Order;

import java.util.Arrays;

public class OrderServiceTest extends OrderAppTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testInsertOrders() {
        Order o1 = new Order().setStatus("未支付").setAddressId(1L).setField1("xxx");
        Order o2 = new Order().setStatus("未支付").setAddressId(1L).setField1("yyy");
        Order o3 = new Order().setStatus("未支付").setAddressId(1L).setField1("yyy");
        Order o4 = new Order().setStatus("未支付").setAddressId(1L).setField1("zzzz");

        o1.setUserId(1L);
        o2.setUserId(3L);
        o3.setUserId(2L);
        o4.setUserId(4L);

        orderService.saveBatch(Arrays.asList(o1,o2,o3,o4));

    }

    @Test
    public void deleteAllData() {

        orderService.removeOrders(Wrappers.<Order>lambdaQuery().ge(Order::getCreateTime, "2019-10-01"));


    }

}

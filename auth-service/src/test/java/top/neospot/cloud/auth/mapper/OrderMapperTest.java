package top.neospot.cloud.auth.mapper;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.auth.AuthAppTest;
import top.neospot.cloud.auth.entity.Customer;
import top.neospot.cloud.auth.entity.Order;

import java.util.List;

public class OrderMapperTest extends AuthAppTest {
    @Autowired
    OrderMapper orderMapper;

    @Test
    public void testInsert() {

        Order order = new Order();
        order.setOrderno("123");
        order.setPrice(10.5f);

        Customer customer = new Customer();
        customer.setId(1);
        order.setCustomer(customer);


        orderMapper.insert(order);

    }

    @Test
    public void testSelect() {
        List<Order> orders = orderMapper.selectAll();
        System.out.println(orders);
    }
}

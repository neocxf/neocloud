package top.neospot.cloud.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.order.OrderAppTest;
import top.neospot.cloud.order.entity.OrderEntity;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/27.
 */
public class OrderEntityMapperTest extends OrderAppTest {

    @Autowired
    private OrderEntityMapper orderMapper;

    @Before
    public void init() {
        QueryWrapper<OrderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OrderEntity::getUserId, 13L);

        OrderEntity order = orderMapper.selectOne(queryWrapper);

        System.out.println("---------------------------------------");

        if (order == null) {
            order = new OrderEntity();
            order.setStatus("未支付");
            order.setStatus("item2");
            order.setUserId(13L);
            orderMapper.insert(order);
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<OrderEntity> orderList = orderMapper.selectList(null);
        orderList.forEach(System.out::println);
    }

}


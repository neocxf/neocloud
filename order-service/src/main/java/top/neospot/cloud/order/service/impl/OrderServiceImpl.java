package top.neospot.cloud.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.mapper.OrderMapper;
import top.neospot.cloud.order.service.OrderService;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/30.
 */
@Component
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

}

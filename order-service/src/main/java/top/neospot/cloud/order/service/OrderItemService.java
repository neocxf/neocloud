package top.neospot.cloud.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Component;
import top.neospot.cloud.order.entity.OrderItem;
import top.neospot.cloud.order.mapper.OrderItemMapper;

@Component
public class OrderItemService extends ServiceImpl<OrderItemMapper, OrderItem> {
}

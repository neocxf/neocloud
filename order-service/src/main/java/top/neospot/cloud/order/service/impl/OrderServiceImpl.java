package top.neospot.cloud.order.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.order.entity.Order;
import top.neospot.cloud.order.entity.OrderEntity;
import top.neospot.cloud.order.mapper.OrderMapper;
import top.neospot.cloud.order.service.OrderService;

import java.util.Timer;
import java.util.TimerTask;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/30.
 */
@Component
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
}

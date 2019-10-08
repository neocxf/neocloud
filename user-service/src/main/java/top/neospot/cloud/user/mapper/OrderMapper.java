package top.neospot.cloud.user.mapper;

import top.neospot.cloud.user.entity.Order;

import java.util.List;

public interface OrderMapper {
    void insert(Order order);
    Order selectOne(Long id);
    List<Order> selectAll();
}

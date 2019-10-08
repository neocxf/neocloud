package top.neospot.cloud.auth.mapper;

import top.neospot.cloud.auth.entity.Order;

import java.util.List;

public interface OrderMapper {
    void insert(Order order);
    Order selectOne(Long id);
    List<Order> selectAll();
}

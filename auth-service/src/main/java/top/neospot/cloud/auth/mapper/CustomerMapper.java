package top.neospot.cloud.auth.mapper;

import top.neospot.cloud.auth.entity.Customer;
import top.neospot.cloud.auth.entity.Order;

import java.util.List;

public interface CustomerMapper {
    void insert(Customer customer);
    Customer selectOne(Long id);
}

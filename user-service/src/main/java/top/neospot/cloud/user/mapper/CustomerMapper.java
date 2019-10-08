package top.neospot.cloud.user.mapper;

import top.neospot.cloud.user.entity.Customer;

public interface CustomerMapper {
    void insert(Customer customer);
    Customer selectOne(Long id);
}

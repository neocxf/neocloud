package top.neospot.cloud.auth.mapper;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.auth.AuthAppTest;
import top.neospot.cloud.auth.entity.Customer;
import top.neospot.cloud.auth.entity.Order;

public class CustomerMapperTest extends AuthAppTest {
    @Autowired
    CustomerMapper customerMapper;

    @Test
    public void testInsert() {

        Customer customer = new Customer();
        customer.setName("neo");
        customer.setAge(30);


        customerMapper.insert(customer);

    }
    @Test
    public void testSelect() {
        Customer customer = customerMapper.selectOne(1L);

        System.out.println(customer);
    }
}

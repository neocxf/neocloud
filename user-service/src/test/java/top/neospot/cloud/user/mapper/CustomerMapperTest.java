package top.neospot.cloud.user.mapper;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.user.UserAppTest;
import top.neospot.cloud.user.entity.Customer;

public class CustomerMapperTest extends UserAppTest {
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

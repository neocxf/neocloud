package top.neospot.cloud.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringBootApplication
public class CustomerEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(CustomerEndpoint.class, args);
    }
}

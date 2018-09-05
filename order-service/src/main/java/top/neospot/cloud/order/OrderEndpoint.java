package top.neospot.cloud.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringBootApplication
public class OrderEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(OrderEndpoint.class, args);
    }
}

package top.neospot.cloud.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@EnableCircuitBreaker
@EnableFeignClients
public class InventoryEndpoint {

    public static void main(String[] args) {
        SpringApplication.run(InventoryEndpoint.class, args);
    }

}

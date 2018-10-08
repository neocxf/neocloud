package top.neospot.cloud.order;

import com.google.common.collect.Maps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.BaseCloud;

import java.util.Map;
import java.util.UUID;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringBootApplication
@EnableSwagger2
@EnableCircuitBreaker
public class OrderEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(OrderEndpoint.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }

    @RestController
    public static class OrderController {
        @GetMapping("/orders")
        public Map<String, String> getAllOrders() {
            Map<String, String> map = Maps.newHashMap();
            String id = UUID.randomUUID().toString();

            map.put("id", UUID.randomUUID().toString());
            map.put("order", "order" + id);

            return map;
        }
    }
}

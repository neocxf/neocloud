package top.neospot.cloud.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.BaseCloud;

/**
 *  http://wuwenliang.net/categories/Sharding-JDBC/
 *
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringCloudApplication
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2
@MapperScan("top.neospot.cloud.order.mapper")
public class OrderApp extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(OrderApp.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }

}

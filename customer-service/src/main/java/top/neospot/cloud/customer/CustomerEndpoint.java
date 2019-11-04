package top.neospot.cloud.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.BaseCloud;
import top.neospot.cloud.customer.remote.RewardServiceClient;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2
public class CustomerEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(CustomerEndpoint.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }

    @Autowired
    RewardServiceClient rewardServiceClient;

    @Override
    public void run(String... args) {
        super.run(args);

        try {
            List<Object> allReward = rewardServiceClient.findAllReward();
            System.out.println(allReward);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

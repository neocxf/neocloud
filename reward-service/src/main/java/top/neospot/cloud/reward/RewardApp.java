package top.neospot.cloud.reward;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;

import java.util.Collections;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@MapperScan("top.neospot.cloud.reward.mapper")
@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2
@Slf4j
public class RewardApp extends SpringBootServletInitializer implements CommandLineRunner  {

    public static void main(String[] args) {
        SpringApplication.run(RewardApp.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build()
                .apiInfo(metaData())
                ;
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Online Store",
                "1.0",
                "Terms of service",
                new Contact("John Thompson", "https://springframework.guru/about/", "john@springfrmework.guru"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                Collections.emptyList()
        );
    }

    @Reference(version = "${demo.service.version}")
    private RpTransactionMessageService rpTransactionMessageService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("hello");
        System.out.println(rpTransactionMessageService.sayHello("neo"));
    }
}

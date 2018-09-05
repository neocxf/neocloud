package top.neospot.cloud.util;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import top.neospot.cloud.common.BaseCloud;
import top.neospot.cloud.util.service.GreeterService;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
@SpringBootApplication
@EnableEurekaClient
public class UtilService extends BaseCloud {

    public static void main(String[] args) {
        SpringApplication.run(UtilService.class, args);
    }


    @Bean
    public GreeterService greeterService() {
        return new GreeterService();
    }


}

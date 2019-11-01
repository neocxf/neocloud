package top.neospot.cloud.messaging;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@MapperScan("top.neospot.cloud.messaging.mapper")
@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2
public class MessagingApp extends BaseCloud {

    public static void main(String[] args) {
        // Launch the application
        SpringApplication.run(MessagingApp.class, args);
    }
}

package top.neospot.cloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/7/10.
 */
@SpringCloudApplication
@MapperScan("top.neospot.cloud.user.mapper")
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}

package top.neospot.cloud.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/7/10.
 */
@SpringCloudApplication
@MapperScan("top.neospot.cloud.auth.mapper")
public class AuthApp {

    public static void main(String[] args) {
        SpringApplication.run(AuthApp.class, args);
    }
}

package top.neospot.cloud.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/7/10.
 */
@SpringBootApplication
@MapperScan("top.neospot.cloud.user.mapper")
public class UserApp {

    public static void main(String[] args) {
        SpringApplication.run(UserApp.class, args);
    }
}

package top.neospot.cloud.stats;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.ReentrantLock;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/10/15.
 */
@SpringBootApplication
@MapperScan("top.neospot.cloud.stats")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        ReentrantLock lock;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

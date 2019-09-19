package top.neospot.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringBootApplication
@EnableEurekaServer
@Slf4j
public class EurekaRegistry  {
    public static void main(String[] args) {
        SpringApplication.run(EurekaRegistry.class, args);

    }
}

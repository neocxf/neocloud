package top.neospot.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/31.
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigServer extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}

package top.neospot.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/31.
 */
@SpringCloudApplication
@EnableConfigServer
public class ConfigServer  {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class, args);
    }
}

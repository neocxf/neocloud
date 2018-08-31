package top.neospot.cloud.cart.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/31.
 */
@Configuration
@Data
public class RemoteConfig {
    @Value("${a: __a}")
    private String a;


}

package top.neospot.cloud.cart.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/31.
 */
@Configuration
@RefreshScope
@Data
public class RemoteConfig {
    @Value("${forbidden: __a}")
    private String a;


}

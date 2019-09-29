package top.neospot.cloud.inventory.annotation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/21.
 */
@ConfigurationProperties("datasouce.mapper")
@Data
@Configuration
public class DataSourceMapper {
    private DbEnum type;
    private String url;
    private String user;
    private String pass;
    private String id;
    private String driverClassName;
}

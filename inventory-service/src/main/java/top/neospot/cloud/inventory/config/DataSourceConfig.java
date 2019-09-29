package top.neospot.cloud.inventory.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import top.neospot.cloud.inventory.annotation.DataSourceMapper;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/21.
 */
@Configuration
public class DataSourceConfig {

//    @Bean
    DataSource dataSource(DataSourceMapper mapper) {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(mapper.getDriverClassName());
        dataSourceBuilder.url(mapper.getUrl());
        dataSourceBuilder.username(mapper.getUser());
        dataSourceBuilder.password(mapper.getPass());
        return dataSourceBuilder.build();
    }

    @Bean
    JedisCluster jedisCluster() {
        Set<HostAndPort> hostAndPorts = new HashSet<>();
        hostAndPorts.add(new HostAndPort("192.168.56.2", 7001));
        hostAndPorts.add(new HostAndPort("192.168.56.2", 7002));
        hostAndPorts.add(new HostAndPort("192.168.56.3", 7003));
        hostAndPorts.add(new HostAndPort("192.168.56.3", 7004));
        hostAndPorts.add(new HostAndPort("192.168.56.4", 7005));
        hostAndPorts.add(new HostAndPort("192.168.56.4", 7006));
        hostAndPorts.add(new HostAndPort("192.168.56.5", 7007));
        hostAndPorts.add(new HostAndPort("192.168.56.5", 7008));
        return new JedisCluster(hostAndPorts);
    }
}

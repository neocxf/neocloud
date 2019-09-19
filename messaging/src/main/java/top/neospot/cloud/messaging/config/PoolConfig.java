package top.neospot.cloud.messaging.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@Configuration
public class PoolConfig {

    @Bean
    public ThreadPoolExecutor feedPool() {
        ThreadPoolExecutorFactoryBean factoryBean = new ThreadPoolExecutorFactoryBean();
        factoryBean.setCorePoolSize(3);
        factoryBean.setMaxPoolSize(6);
        factoryBean.setQueueCapacity(100);
        factoryBean.setThreadNamePrefix("neo_cloud_");
        factoryBean.initialize();
        return (ThreadPoolExecutor) factoryBean.getObject();
    }
}

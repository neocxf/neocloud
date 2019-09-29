package top.neospot.cloud.order.config;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/23.
 */
@Configuration
public class ZkLockConfig {

    @Bean
    public InterProcessLock interProcessLock(CuratorFramework curatorFramework) {
        return new InterProcessSemaphoreMutex(curatorFramework, "/order-pay");
    }

}

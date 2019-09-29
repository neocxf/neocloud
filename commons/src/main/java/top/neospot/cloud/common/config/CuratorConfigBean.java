package top.neospot.cloud.common.config;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/23.
 */
//@Configuration
//@ConfigurationProperties(prefix = "curator")
@Data
public class CuratorConfigBean {

    private int retryCount;

    private int elapsedTimeMs;

    private String connectString;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;


//    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {

        return CuratorFrameworkFactory.newClient(
                this.getConnectString(),
                this.getSessionTimeoutMs(),
                this.getConnectionTimeoutMs(),
                new ExponentialBackoffRetry(this.getRetryCount(), this.getElapsedTimeMs()));
    }
}

package top.neospot.cloud.common.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactoryBean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Date;

@Configuration
@Slf4j
@MapperScan("top.neospot.cloud.*.mapper.*")
public class MybatisPlusConfig implements MetaObjectHandler {

//    @Bean
    EmbeddedDatabaseFactoryBean dataSource() {
        EmbeddedDatabaseFactoryBean factoryBean = new EmbeddedDatabaseFactoryBean();
        factoryBean.setDatabaseType(EmbeddedDatabaseType.H2);
        factoryBean.setDatabaseName("spring-boot-sample");
        factoryBean.setDatabasePopulator(new ResourceDatabasePopulator(
                new ClassPathResource("database/schema.sql"),
                new ClassPathResource("database/dataload.sql")
        ));
        return factoryBean;
    }


    // 新增的时候自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.setFieldValByName("createTime",new Date(),metaObject);
        this.setFieldValByName("lastUpdateTime", new Date(), metaObject);
    }

	// 更新的税后自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("lastUpdateTime", new Date(),metaObject);
    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // paginationInterceptor.setLimit(你的最大单页限制数量，默认 500 条，小于 0 如 -1 不受限制);
        return paginationInterceptor;
    }


    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

}
package top.neospot.cloud.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringBootApplication
@EnableSwagger2
public class CatalogEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(CatalogEndpoint.class, args);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }
}

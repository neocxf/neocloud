package top.neospot.cloud.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.cart.domain.entity.Cart;
import top.neospot.cloud.cart.repository.CartMapper;
import top.neospot.cloud.common.BaseCloud;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringCloudApplication
@EnableFeignClients
@EnableSwagger2
@Slf4j
public class CartEndpoint extends BaseCloud {

    public static void main(String[] args) {
        SpringApplication.run(CartEndpoint.class, args);
    }

    private CartMapper cartMapper;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    public CartEndpoint(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public void run(String... args) {
        super.run(args);

        System.out.println(this.cartMapper.findById(1L));
        this.cartMapper.save(Cart.builder().lines("3,2,1").userId(1L).build());

        this.cartMapper.findAll().forEach(System.out::println);
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }

}

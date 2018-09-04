package top.neospot.cloud.cart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import top.neospot.cloud.cart.domain.entity.Cart;
import top.neospot.cloud.cart.repository.CartMapper;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@Slf4j
public class CartEndpoint implements CommandLineRunner {

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
        System.out.println(this.cartMapper.findById(1L));
        this.cartMapper.save(Cart.builder().lines("3,2,1").userId(1L).build());

        this.cartMapper.findAll().forEach(System.out::println);
    }


}

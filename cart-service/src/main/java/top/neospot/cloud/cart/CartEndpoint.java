package top.neospot.cloud.cart;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.neospot.cloud.cart.remote.InventoryClient;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class CartEndpoint {

    public static void main(String[] args) {
        SpringApplication.run(CartEndpoint.class, args);
    }

    @RestController("/carts")
    public static class CartController {
        final InventoryClient inventoryClient;

        @Autowired
        public CartController(InventoryClient inventoryClient) {
            this.inventoryClient = inventoryClient;
        }

        @GetMapping
        public List<Map<String,String>> getInventories() {
            System.out.println("request coming");

            List<Map<String,String>> carts = Lists.newArrayList();
            List<Map<String,String>> inventories = inventoryClient.inventoriesList();

            for (int i = 0; i < 5; i++) {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", UUID.randomUUID().toString());
                map.put("cart", "cart"+i);
                map.put("inventoryName", inventories.get(i).get("productName"));

                carts.add(map);
            }

            return carts;
        }

    }
}

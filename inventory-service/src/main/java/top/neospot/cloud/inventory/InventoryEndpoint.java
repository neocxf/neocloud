package top.neospot.cloud.inventory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@SpringBootApplication
@EnableEurekaClient
public class InventoryEndpoint {

    public static void main(String[] args) {
        SpringApplication.run(InventoryEndpoint.class, args);
    }

    @RestController
    @RequestMapping(("/inventory"))
    public static class InventoryController {

        @GetMapping
        public List<Map<String, String>> getInventories() {
            List<Map<String, String>> inventories = Lists.newArrayList();

            for (int i = 0; i < 5; i++) {
                Map<String, String> map = Maps.newHashMap();
                map.put("id", UUID.randomUUID().toString());
                map.put("productName", "product" + i);

                inventories.add(map);
            }

            return inventories;
        }
    }
}

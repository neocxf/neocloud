package top.neospot.cloud.cart.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@FeignClient("neo-inventory")
@Component
public interface InventoryClient {
    @GetMapping("/inventory")
    List<Map<String,String>> inventoriesList();
}

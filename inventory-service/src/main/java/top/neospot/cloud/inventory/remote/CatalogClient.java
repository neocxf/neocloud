package top.neospot.cloud.inventory.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

/**
 *  https://juejin.im/post/5c009ff6f265da614b11b84d
 *
 *  SpringCloud实战3-Hystrix请求熔断与服务降级:
 *  https://www.cnblogs.com/huangjuncong/p/9026949.html
 *
 * By neo.chen{neocxf@gmail.com} on 2018/8/24.
 */
@FeignClient("catalog-service")
public interface CatalogClient {
    @GetMapping("/api/products")
    List<Map<String,String>> catalogList();
}

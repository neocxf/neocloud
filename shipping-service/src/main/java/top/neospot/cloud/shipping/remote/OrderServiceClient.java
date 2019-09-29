package top.neospot.cloud.shipping.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.messaging.model.DeductReward;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
@FeignClient(value = "order-service")
public interface OrderServiceClient {
    @RequestMapping(value = "/orders/{orderId}",method = RequestMethod.PUT)
    public void confirmShipping(@PathVariable("orderId") Long orderId);


}

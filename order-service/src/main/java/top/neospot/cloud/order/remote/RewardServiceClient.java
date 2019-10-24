package top.neospot.cloud.order.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import top.neospot.cloud.messaging.model.DeductReward;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
@FeignClient(value = "reward-service", fallback = RewardServiceClientFallback.class)
public interface RewardServiceClient {
    @RequestMapping(value = "/deductReward",method = RequestMethod.POST)
    void tryDeductReward(@RequestBody DeductReward deductReward) throws Exception;


}

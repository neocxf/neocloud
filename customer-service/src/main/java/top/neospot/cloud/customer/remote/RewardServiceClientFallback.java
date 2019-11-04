package top.neospot.cloud.customer.remote;

import top.neospot.cloud.messaging.model.DeductReward;

import java.util.Collections;
import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
public class RewardServiceClientFallback implements RewardServiceClient {
    @Override
    public void tryDeductReward(DeductReward deductReward) throws Exception {
        throw new RuntimeException("reward-service down ...");
    }

    @Override
    public List<Object> findAllReward() {
        return Collections.emptyList();
    }
}

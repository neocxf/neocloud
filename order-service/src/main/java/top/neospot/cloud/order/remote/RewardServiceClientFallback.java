package top.neospot.cloud.order.remote;

import top.neospot.cloud.messaging.model.DeductReward;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
public class RewardServiceClientFallback implements RewardServiceClient {
    @Override
    public void tryDeductReward(DeductReward deductReward) throws Exception {
        throw new RuntimeException("reward-service down ...");
    }
}

package top.neospot.cloud.reward.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.neospot.cloud.messaging.api.RpTransactionMessageService;
import top.neospot.cloud.messaging.model.DeductReward;
import top.neospot.cloud.reward.entity.Reward;
import top.neospot.cloud.reward.entity.RewardExchange;
import top.neospot.cloud.reward.mapper.RewardExchangeMapper;
import top.neospot.cloud.reward.mapper.RewardMapper;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@RestController
public class RewardController {
    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private RewardExchangeMapper rewardExchangeMapper;

    @Reference
    private RpTransactionMessageService rpTransactionMessageService;

    @GetMapping("/")
    public List<Reward> findAll() {
        return rewardMapper.selectList(null);
    }

    @PostMapping("/deductReward")
    public void tryDeductReward(@RequestBody DeductReward deductReward) throws Exception {

        Reward reward = rewardMapper.selectOne(Wrappers.<Reward>lambdaQuery().eq(Reward::getUserId, deductReward.getUserId()));

        RewardExchange exchange1 = rewardExchangeMapper.selectOne(Wrappers.<RewardExchange>lambdaQuery().eq(RewardExchange::getOrderId, deductReward.getOrderId()));

        if (exchange1 != null) {
            // already deduct, just return
            return;
        }

        if (reward.getCredit() > deductReward.getCredit()) {
            reward.setCredit(reward.getCredit() - deductReward.getCredit());
            rewardMapper.updateById(reward);

            RewardExchange exchange = new RewardExchange();
            exchange.setCredit(deductReward.getCredit());
            exchange.setUserId(deductReward.getUserId());
            exchange.setProductId(deductReward.getProductId());
            exchange.setRewardId(reward.getId());
            exchange.setOrderId(deductReward.getOrderId());

            rewardExchangeMapper.insert(exchange);

            rpTransactionMessageService.confirmAndSendMessage(deductReward.getMessageId());
        } else {
            throw new RuntimeException("insufficient reward balance");
        }
    }

}

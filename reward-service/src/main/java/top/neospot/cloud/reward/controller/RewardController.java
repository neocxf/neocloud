package top.neospot.cloud.reward.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/")
    public List<Reward> findAll() {
        return rewardMapper.selectList(null);
    }

    @PostMapping("/exchange")
    public int exchange(@RequestBody ExchangeDto exchangeDto) {
        RewardExchange exchange = new RewardExchange();
        exchange.setCredit(exchangeDto.credit);
        exchange.setUserId(exchangeDto.getUserId());
        exchange.setProductId(exchangeDto.getProductId());
        return rewardExchangeMapper.insert(exchange);
    }

    @GetMapping("/exchange/")
    public List<RewardExchange> exchangeRecords(@RequestParam("userId") int userId) {
        return rewardExchangeMapper.selectList(Wrappers.<RewardExchange>lambdaQuery().eq(RewardExchange::getUserId, userId));
    }


    @Data
    static class ExchangeDto {
        private int userId;
        private int productId;
        private int credit;
    }
}

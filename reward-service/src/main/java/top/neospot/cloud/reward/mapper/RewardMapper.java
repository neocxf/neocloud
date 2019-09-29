package top.neospot.cloud.reward.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.neospot.cloud.reward.entity.Reward;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
public interface RewardMapper extends BaseMapper<Reward> {
    Reward queryOrderById(Long id);
}

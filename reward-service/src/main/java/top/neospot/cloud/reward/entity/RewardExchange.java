package top.neospot.cloud.reward.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import top.neospot.cloud.common.model.BaseModel;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class RewardExchange extends BaseModel {
    private Long productId;
    private Long userId;
    private Long credit;
    private Long rewardId;
    private Long orderId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

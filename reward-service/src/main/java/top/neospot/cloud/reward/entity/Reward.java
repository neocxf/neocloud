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
public class Reward extends BaseModel {
    private int credit;
    private int userId;
    @Version
    private Integer version = 0;
    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

package top.neospot.cloud.messaging.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/20.
 */
@Data
public class DeductReward {
    private Long number;

    private Long userId;

    private Long credit;

    private Long orderId;

    private String messageId;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

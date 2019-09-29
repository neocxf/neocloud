package top.neospot.cloud.common.model;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class Message extends BaseModel {
    private String editor;
    private String creator;
    @Builder.Default
    private Date editTime = new Date();
    private String messageId;
    private String messageBody;
    private String messageDateType;
    private String consumerQueue;
    private Integer messageSendTimes;
    private Boolean alreadyDead;
    /**
     * WAITING_CONFIRM: wait the transaction to succeed and send the message
     * SENDING: send the message to the downstream
     */
    private String status;
    private String remark;
    private String field;

    public synchronized void incrementTimes() {
        messageSendTimes ++;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

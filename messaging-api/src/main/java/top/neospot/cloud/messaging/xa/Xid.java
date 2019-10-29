package top.neospot.cloud.messaging.xa;

import com.alibaba.fastjson.JSONObject;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Xid implements Serializable {
    private String value;
    @Builder.Default
    private Date createTime = new Date();
    @Builder.Default
    private TransState status = TransState.NEW;


    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}

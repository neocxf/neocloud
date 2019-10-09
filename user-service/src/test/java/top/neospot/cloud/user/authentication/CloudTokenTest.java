package top.neospot.cloud.user.authentication;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Date;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/10/9.
 */
public class CloudTokenTest {

    @Test
    public void testDeserialize() {
        CloudToken cloudToken = new CloudToken("111");
        cloudToken.setExpiredAt(new Date());
        cloudToken.setUsername("neo");
        cloudToken.setHost("localhost");

        String cloudTokenStr = JSONObject.toJSONString(cloudToken);
        System.out.println(cloudTokenStr);

        CloudToken parseObject = JSONObject.parseObject(cloudTokenStr, CloudToken.class);

        System.out.println(parseObject);
    }

}

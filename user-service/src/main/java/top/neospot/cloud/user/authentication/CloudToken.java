package top.neospot.cloud.user.authentication;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authc.HostAuthenticationToken;

import java.util.Date;

public class CloudToken implements HostAuthenticationToken {
	private static final long serialVersionUID = 9217639903967592166L;

	private String token;
    private String host;
    private String username;
    private Date expiredAt;

    public CloudToken(String token) {
        this(token, null);
    }

    public CloudToken(String token, String host) {
        this.token = token;
        this.host = host;
    }

    public CloudToken() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public String toString(){
        return JSONObject.toJSONString(this);
    }
}

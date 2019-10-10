package top.neospot.cloud.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.auth.entity.UserInfo;
import top.neospot.cloud.auth.mapper.UserInfoMapper;
import top.neospot.cloud.auth.service.UserInfoService;
import top.neospot.cloud.auth.utils.MD5Utils;
import top.neospot.cloud.common.exceptions.CloudBizException;

import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    private static final String USER_PREFIX = "neocloud:auth:userinfo:";
    private static final Integer ttlInSec = 60 * 30;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public UserInfo findByUsername(String username) {

        return getBaseMapper().selectOneByUsername(username);
    }

    @Override
    public UserInfo findByUsernameFromCache(String username) {
        String userInfoString = (String) redisTemplate.boundValueOps(USER_PREFIX + username).get();
        UserInfo userInfo = null;
        if (userInfoString != null) {
            userInfo = JSON.parseObject(userInfoString, UserInfo.class);
            return userInfo;
        }
        return null;
    }

    @Override
    public UserInfo checkUserAuthenticated(String username) {
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        return userInfo;
    }

    @Override
    @Transactional
    public UserInfo regist(String username, String password) throws Exception {
        UserInfo user = findByUsername(username);

        if (user != null) {
            throw new CloudBizException(String.format("username [%s] already exists, try another name or use this username to login", username));
        }

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();


        String encodePassword = MD5Utils.encrypt(username, password,salt);

        user = new UserInfo();
        user.setPassword(encodePassword);
        user.setUsername(username);
        user.setSalt(salt);

        this.save(user);

        getBaseMapper().addRole(user.getId(), 1);

        return user;

    }
}
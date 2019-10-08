package top.neospot.cloud.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.common.exceptions.CloudBizException;
import top.neospot.cloud.user.authentication.JwtUtils;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;
import top.neospot.cloud.user.mapper.UserInfoMapper;
import top.neospot.cloud.user.service.UserService;
import top.neospot.cloud.user.utils.MD5Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserService {
    private static final String USER_PREFIX = "neocloud:auth:userinfo:";
    private static final Integer ttlInSec = 60 * 30;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Override
    public UserInfo findByUsername(String username) {
        UserInfo userInfo;
        // last option, get from db
        userInfo = getBaseMapper().selectOneCascadeByUsername(username);
        return userInfo;
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

    /**
     * 保存user登录信息，返回token
     * @param username
     */
    @Override
    public String generateJwtToken(String username) {
        String salt = JwtUtils.generateSalt();
        redisTemplate.opsForValue().set("token:salt:"+username, salt, 3600, TimeUnit.SECONDS);
        return JwtUtils.sign(username, salt, 3600); //生成jwt token，设置过期时间为1小时
    }


    @Override
    @Transactional
    public UserInfo regist(String username, String password) throws Exception {
        UserInfo user = findByUsername(username);

        if (user != null) {
            throw new CloudBizException(String.format("username [%s] already exists, try another name or use this username to login", username));
        }

        String salt = JwtUtils.generateSalt();

        String encodePassword = MD5Utils.encrypt(username, password,salt);

        user = new UserInfo();
        user.setPassword(encodePassword);
        user.setUsername(username);
        user.setSalt(salt);

        this.save(user);

        getBaseMapper().addRole(user.getId(), 1);

        return user;

    }

    @Override
    public UserInfo getJwtTokenInfo(String username) {
        String salt = redisTemplate.opsForValue().get("token:salt:"+username);

        if (salt == null) return null;

        UserInfo user = getBaseMapper().selectOne(Wrappers.<UserInfo>lambdaQuery().eq(UserInfo::getUsername, username));
        user.setTokenSalt(salt);
        return user;
    }

    @Override
    public List<SysRole> findRolePermissionsByUsername(String username) {
        return getBaseMapper().selectAllRolePermissions(username);
    }

}
package top.neospot.cloud.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.neospot.cloud.user.entity.SysPermission;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;
import top.neospot.cloud.user.service.UserService;

import javax.annotation.Resource;

@Slf4j
public class DBShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    public DBShiroRealm(UserService userService) {
        this.userService = userService;
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 能进入这里说明用户已经通过验证了
        UserInfo userInfo = (UserInfo) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        for (SysRole role : userInfo.getRoles()) {
            simpleAuthorizationInfo.addRole(role.getName());
            for (SysPermission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户输入的账户
        String principals = (String) authenticationToken.getPrincipal();

        log.info("getting userinfo: {}" , authenticationToken.getPrincipal());


        // 通过username从数据库中查找 UserInfo 对象
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userService.findByUsername(principals);
        if (null == userInfo) {
            throw new AuthenticationException("用户名或者密码错误");
        }

        String password = userInfo.getPassword(); //database password, later shiro will try to authenticate it
        String salt = userInfo.getSalt();

        userInfo.setSalt("secret");
        userInfo.setPassword("******");

        // here returns the actual authentication info. Shiro will later compare the password using hash-salt algorithm
        return new SimpleAuthenticationInfo(
                userInfo, // 用户名
                password, // 密码
                ByteSource.Util.bytes(principals + salt), // salt=username+salt
                getName() // realm name
        );
    }




}
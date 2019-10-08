package top.neospot.cloud.auth.authentication;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import top.neospot.cloud.auth.entity.SysPermission;
import top.neospot.cloud.auth.entity.SysRole;
import top.neospot.cloud.auth.entity.UserInfo;
import top.neospot.cloud.auth.service.UserInfoService;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userInfoService;

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
        String username = (String) authenticationToken.getPrincipal();
        log.info("getting userinfo: {}" , authenticationToken.getPrincipal());


        // 通过username从数据库中查找 UserInfo 对象
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (null == userInfo) {
            return null;
        }

        String password = userInfo.getPassword();
        String salt = userInfo.getSalt();

        userInfo.setSalt("secret");
        userInfo.setPassword("******");

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                userInfo, // 用户名
                password, // 密码
                ByteSource.Util.bytes(username + salt), // salt=username+salt
                getName() // realm name
        );
        return simpleAuthenticationInfo;
    }

    /**
     * 模拟从数据库中获取权限数据
     *
     * @param userName
     * @return
     */
    private Set<String> getPermissionsByUserName(String userName) {
        Set<String> permissions = new HashSet<>();
        permissions.add("user:delete");
        permissions.add("user:add");
        return permissions;
    }

    /**
     * 模拟从数据库中获取角色数据
     *
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        roles.add("user");
        return roles;
    }

    /**
     * 模拟从数据库取凭证的过程
     *
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        return "";
    }
}
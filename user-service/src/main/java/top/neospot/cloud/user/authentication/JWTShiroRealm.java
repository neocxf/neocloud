package top.neospot.cloud.user.authentication;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import top.neospot.cloud.user.entity.SysPermission;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;
import top.neospot.cloud.user.service.UserService;


/**
 * 自定义身份认证
 * 基于HMAC（ 散列消息认证码）的控制域
 */
@Slf4j
public class JWTShiroRealm extends AuthorizingRealm {

    protected UserService userService;

    public JWTShiroRealm(UserService userService){
        this.userService = userService;
        this.setCredentialsMatcher(new JWTCredentialsMatcher());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 认证信息.(身份验证) : Authentication 是用来验证用户身份
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        JWTToken jwtToken = (JWTToken) authcToken;
        String token = jwtToken.getToken();
        
        UserInfo user = userService.getJwtTokenInfo(JwtUtils.getUsername(token));
        if(user == null)
            throw new AuthenticationException("token过期，请重新登录");

        return new SimpleAuthenticationInfo(user, user.getEmbedToken(), "jwtRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        UserInfo user = (UserInfo) principals.getPrimaryPrincipal();

        for (SysRole role : userService.findRolePermissionsByUsername(user.getUsername())) {
            simpleAuthorizationInfo.addRole(role.getName());
            for (SysPermission permission : role.getPermissions()) {
                simpleAuthorizationInfo.addStringPermission(permission.getName());
            }
        }

        return simpleAuthorizationInfo;
    }
}

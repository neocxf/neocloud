package top.neospot.cloud.user.authentication;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.apache.shiro.web.subject.WebSubject.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;
import top.neospot.cloud.user.entity.UserInfo;
import top.neospot.cloud.user.service.UserInfoService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 过滤请求头部信息，如果有，就自动登录 http://blog.csdn.net/qi923701/article/details/75007813
 *
 *  https://www.cnblogs.com/nwgdk/p/11116328.html
 * @author wutao
 * @date 2017年11月11日 下午3:09:51
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

    @Autowired
    private UserInfoService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader(JWTUtil.AUTH_TOKEN);
        if (StringUtils.isNotBlank(tokenHeader)) {
            String  username = JWTUtil.getUsername(tokenHeader);
            if (username != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug("retrieve username {} from token", username);
                }

                UserInfo auser = userService.findByUsernameFromCache(username);
                if (auser != null) {
                    PrincipalCollection principals = new SimplePrincipalCollection(auser, "authorizingRealm");
                    Builder builder = new WebSubject.Builder(request, response);
                    builder.principals(principals);
                    builder.authenticated(true);
                    WebSubject subject = builder.buildWebSubject();

                    // TODO: is already authenticated
                    ThreadContext.bind(subject);
                }

            }
        }
        chain.doFilter(request, response);
    }

}
package top.neospot.cloud.user.config;

import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSessionStorageEvaluator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.neospot.cloud.user.authentication.DBShiroRealm;
import top.neospot.cloud.user.authentication.JWTShiroRealm;
import top.neospot.cloud.user.filters.AnyRolesAuthorizationFilter;
import top.neospot.cloud.user.filters.JwtAuthFilter;
import top.neospot.cloud.user.service.UserService;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * shiro配置类
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean<Filter> filterRegistrationBean(SecurityManager securityManager, UserService userService) throws Exception{
        FilterRegistrationBean<Filter> filterRegistration = new FilterRegistrationBean<Filter>();
        filterRegistration.setFilter((Filter) Objects.requireNonNull(shiroFilter(securityManager, userService).getObject()));
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setAsyncSupported(true);
        filterRegistration.setEnabled(true);
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC);

        return filterRegistration;
    }

    @Bean
    public Authenticator authenticator(UserService userService) {
        ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        authenticator.setRealms(Arrays.asList(jwtShiroRealm(userService), dbShiroRealm(userService)));
        authenticator.setAuthenticationStrategy(new FirstSuccessfulStrategy());
        return authenticator;
    }

    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator(){
        DefaultWebSessionStorageEvaluator sessionStorageEvaluator = new DefaultWebSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5"); // 散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2); // 散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }

    @Bean("dbRealm")
    public Realm dbShiroRealm(UserService userService) {
        DBShiroRealm dbShiroRealm = new DBShiroRealm(userService);
        dbShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return dbShiroRealm;
    }

    @Bean("jwtRealm")
    public Realm jwtShiroRealm(UserService userService) {
        JWTShiroRealm myShiroRealm = new JWTShiroRealm(userService);
        return myShiroRealm;
    }

    /**
     * 设置过滤器
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager, UserService userService) {
    	ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        Map<String, Filter> filterMap = factoryBean.getFilters();
        filterMap.put("authcToken", createAuthFilter(userService));
        filterMap.put("anyRole", createRolesFilter());
        factoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        factoryBean.setFilters(filterMap);

        return factoryBean;
    }

    @Bean
    protected ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/welcome", "anon");
        chainDefinition.addPathDefinition("/regist", "anon");
        chainDefinition.addPathDefinition("/h2-console/**", "anon");
        chainDefinition.addPathDefinition("/static/**", "anon");
        chainDefinition.addPathDefinition("/login", "noSessionCreation,anon");
        chainDefinition.addPathDefinition("/logout", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/image/**", "anon");
        chainDefinition.addPathDefinition("/admin/**", "noSessionCreation,authcToken,anyRole[admin,manager]"); //只允许admin或manager角色的用户访问
        chainDefinition.addPathDefinition("/article/list", "noSessionCreation,authcToken");
        chainDefinition.addPathDefinition("/article/*", "noSessionCreation,authcToken[permissive]");
        chainDefinition.addPathDefinition("/**", "noSessionCreation,authcToken");


        return chainDefinition;
    }

    protected JwtAuthFilter createAuthFilter(UserService userService){
        return new JwtAuthFilter(userService);
    }

    protected AnyRolesAuthorizationFilter createRolesFilter(){
        return new AnyRolesAuthorizationFilter();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}

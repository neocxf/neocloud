package top.neospot.cloud.user.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/7/9.
 */
public class ShiroTest {
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
    SimpleAccountRealm simpleAccountRealm2 = new SimpleAccountRealm();

    @Before
    public void init() {
        simpleAccountRealm.addAccount("neo", "pass");
        simpleAccountRealm2.addAccount("neo", "pass", "admin", "user");
    }

    @Test
    public void test1() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("neo", "pass");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出false

    }

    @Test
    public void test2() {
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm2);

        SecurityUtils.setSecurityManager(defaultSecurityManager);

        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("neo", "pass");
        subject.login(token);

        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRoles("admin","user");
        subject.checkRoles("guest");

        subject.logout();

        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出false

    }

    @Test
    public void testAuthentication() {

        MyRealm myRealm = new MyRealm(); // 实现自己的 Realm 实例

        // 1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(myRealm);

        // 2.主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager); // 设置SecurityManager环境
        Subject subject = SecurityUtils.getSubject(); // 获取当前主体

        UsernamePasswordToken token = new UsernamePasswordToken("wmyskxz", "123456");
        subject.login(token); // 登录

        // subject.isAuthenticated()方法返回一个boolean值,用于判断用户是否认证成功
        System.out.println("isAuthenticated:" + subject.isAuthenticated()); // 输出true
        // 判断subject是否具有admin和user两个角色权限,如没有则会报错
        subject.checkRoles("admin", "user");
//        subject.checkRole("xxx"); // 报错
        // 判断subject是否具有user:add权限
        subject.checkPermission("user:add");
    }

    @Test
    public void testHashSalt() {
        String password = "123456";
//        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String salt = "xbNIxrQfn6COSYn1/GdloA==";
        int times = 2;  // 加密次数：2
        String alogrithmName = "md5";   // 加密算法

        String encodePassword = new SimpleHash(alogrithmName, password, "admin" + salt, times).toString();

        System.out.printf("原始密码是 %s , 盐是： %s, 运算次数是： %d, 运算出来的密文是：%s ",password,salt,times,encodePassword);
    }
}

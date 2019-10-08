package top.neospot.cloud.user.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.neospot.cloud.user.entity.UserInfo;
import top.neospot.cloud.user.pojo.ResponseBo;
import top.neospot.cloud.user.service.UserInfoService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserInfoService userInfoService;

    @RequestMapping("/success")
    @ResponseBody
    public ResponseBo success() {
        Map<String,Object> result = new HashMap<>();
        result.put("status", "OK");
        result.put("authenticated", "true");
        result.put("privileges", "all");
        result.put("principal",  SecurityUtils.getSubject().getPrincipal());

        return ResponseBo.ok(result);
    }

    @RequestMapping({"/","/index"})
    public String index(){
        return"/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception{
        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> "+exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

	@PostMapping("/login")
	@ResponseBody
	public ResponseBo login(String username, String password, Boolean rememberMe) {
		UsernamePasswordToken token = new UsernamePasswordToken(username, password, rememberMe);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return ResponseBo.ok();
		} catch (UnknownAccountException | IncorrectCredentialsException | LockedAccountException e) {
            System.out.println(subject.isAuthenticated());
            System.out.println(subject.getPrincipal());
            System.out.println(subject.getPreviousPrincipals());
            System.out.println(subject.getPrincipals());
			return ResponseBo.error(e.getMessage());
		} catch (AuthenticationException e) {
			return ResponseBo.error("认证失败！");
		}


	}

	@PostMapping("/regist")
    @ResponseBody
    public ResponseBo regist(String username, String password) {
        UserInfo regist = null;
        try {
            regist = userInfoService.regist(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseBo.ok(regist);

    }

    @RequestMapping("/403")
    public String unauthorizedRole(){
        System.out.println("------没有权限-------");
        return "403";
    }
}
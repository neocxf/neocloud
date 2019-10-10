package top.neospot.cloud.auth.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import top.neospot.cloud.auth.entity.UserInfo;
import top.neospot.cloud.auth.service.UserInfoService;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserInfoController {

    @Resource
    UserInfoService userInfoService;

    /**
     * 按username账户从数据库中取出用户信息
     *
     * @param username 账户
     * @return
     */
    @GetMapping("/userList")
    @RequiresPermissions("userInfo:view") // 权限管理.
    public List<UserInfo> findUserInfoByUsername() {
        return userInfoService.list();
    }

    /**
     * 简单模拟从数据库添加用户信息成功
     *
     * @return
     */
    @PostMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public String addUserInfo() {
        return "addUserInfo success!";
    }

    /**
     * 简单模拟从数据库删除用户成功
     *
     * @return
     */
    @DeleteMapping("/userDelete")
    @RequiresPermissions("userInfo:delete")
    public String deleteUserInfo() {
        return "deleteUserInfo success!";
    }
}
package top.neospot.cloud.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfo {
    private String username; // 登录账户,唯一.
    private String name; // 名称(匿名或真实姓名),用于UI显示
    private String password; // 密码.
    private String salt; // 加密密码的盐
    @JsonIgnoreProperties(value = {"userInfos"})
    private List<SysRole> roles; // 一个用户具有多个角色

    /** getter and setter */
}
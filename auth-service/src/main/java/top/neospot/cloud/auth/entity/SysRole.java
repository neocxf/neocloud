package top.neospot.cloud.auth.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class SysRole {
    @TableId
    private Long id;
    private String name; // 角色名称,如 admin/user
    private String description; // 角色描述,用于UI显示

    // 角色 -- 权限关系：多对多
    @JsonIgnoreProperties(value = {"roles"})
    private transient List<SysPermission> permissions;

    // 用户 -- 角色关系：多对多
    @JsonIgnoreProperties(value = {"roles"})
    private transient List<UserInfo> userInfos;// 一个角色对应多个用户

    /** getter and setter */

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
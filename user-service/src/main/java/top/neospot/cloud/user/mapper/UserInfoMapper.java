package top.neospot.cloud.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo selectOneById(Long id);
    UserInfo selectOneCascadeByUsername(String username);
    List<SysRole> selectAllRolePermissions(String username);
    List<UserInfo> selectAll();
    int deleteRolesByUserId(Long id);
    int deleteRole(long id, Long roleId) ;
    int deleteRoleByRoleName(long id, String roleName);
    void addRole(long id, long roleId);
}
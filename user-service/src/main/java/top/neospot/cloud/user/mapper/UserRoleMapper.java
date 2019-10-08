package top.neospot.cloud.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<SysRole> {
    SysRole selectOneById(Long id);
    SysRole selectOneByRoleName(String username);
    List<UserInfo> selectAll();
    int deletePermissionsByRoleId(Long id);
    int deletePermission(long id, Long permissionId) ;
    int deletePermissionByPermissionName(long id, String permissionName);
}
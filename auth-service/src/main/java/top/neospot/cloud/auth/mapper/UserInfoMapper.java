package top.neospot.cloud.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.auth.entity.UserInfo;

import java.util.List;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    UserInfo selectOneById(Long id);
    UserInfo selectOneByUsername(String username);
    List<UserInfo> selectAll();
    int deleteRolesByUserId(Long id);
    int deleteRole(long id, Long roleId) ;
    int deleteRoleByRoleName(long id, String roleName);
    void addRole(long id, long roleId);
}
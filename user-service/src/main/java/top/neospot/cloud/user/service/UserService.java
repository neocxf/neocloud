package top.neospot.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.user.entity.SysRole;
import top.neospot.cloud.user.entity.UserInfo;

import java.util.List;

public interface UserService extends IService<UserInfo> {
    /** 通过username查找用户信息；*/
    UserInfo findByUsername(String username);

    UserInfo checkUserAuthenticated(String username);

    String generateJwtToken(String username);

    String generateCloudToken(String username);

    UserInfo getCloudTokenInfo(String username);

    @Transactional
    UserInfo regist(String username, String password) throws Exception;

    UserInfo getJwtTokenInfo(String username);

    List<SysRole> findRolePermissionsByUsername(String username);
}
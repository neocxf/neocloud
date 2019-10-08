package top.neospot.cloud.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.user.entity.UserInfo;

public interface UserInfoService extends IService<UserInfo> {
    /** 通过username查找用户信息；*/
    UserInfo findByUsername(String username);

    UserInfo findByUsernameFromCache(String username);

    UserInfo checkUserAuthenticated(String username);

    @Transactional
    UserInfo regist(String username, String password) throws Exception;

}
package top.neospot.cloud.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.transaction.annotation.Transactional;
import top.neospot.cloud.auth.entity.UserInfo;

public interface UserInfoDao extends BaseMapper<UserInfo> {
    /** 通过username查找用户信息*/
    @Transactional
    UserInfo findByUsername(String username);
}
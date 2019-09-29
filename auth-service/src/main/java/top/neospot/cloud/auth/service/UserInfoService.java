package top.neospot.cloud.auth.service;

import top.neospot.cloud.auth.entity.UserInfo;

public interface UserInfoService {
    /** 通过username查找用户信息；*/
    public UserInfo findByUsername(String username);
}
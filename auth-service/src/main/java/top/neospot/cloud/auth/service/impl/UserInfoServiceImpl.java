package top.neospot.cloud.auth.service.impl;

import org.springframework.stereotype.Service;
import top.neospot.cloud.auth.entity.UserInfo;
import top.neospot.cloud.auth.repository.UserInfoDao;
import top.neospot.cloud.auth.service.UserInfoService;

import javax.annotation.Resource;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Resource
    UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        return userInfoDao.findByUsername(username);
    }
}
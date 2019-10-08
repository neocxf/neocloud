package top.neospot.cloud.user.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.user.UserAppTest;
import top.neospot.cloud.user.entity.UserInfo;

public class UserInfoMapperTest extends UserAppTest {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Test
    public void testSelect() {
        UserInfo userInfo = userInfoMapper.selectOneByUsername("wmyskxz");
        System.out.println(userInfo);
        System.out.println(userInfo.getRoles());

        userInfo.getRoles().forEach(role -> {
            System.out.println(role);
            role.getPermissions().forEach(System.out::println);
        });
    }

    @Test
    public void testInsert() {
        UserInfo user = new UserInfo();
        user.setUsername("neo4");
        user.setPassword("neo");
        user.setSalt("salt");

        userInfoMapper.insert(user);
    }
}

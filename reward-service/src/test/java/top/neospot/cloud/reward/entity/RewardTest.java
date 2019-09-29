package top.neospot.cloud.reward.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.reward.RewardAppTest;
import top.neospot.cloud.reward.mapper.RewardMapper;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
public class RewardTest extends RewardAppTest {
    @Autowired
    private RewardMapper rewardMapper;

    @Before
    public void init() {
        QueryWrapper<Reward> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reward::getUserId, 1)   ;

        Reward user = rewardMapper.selectOne(queryWrapper);

        if (user == null) {
            user = new Reward().setUserId(1L).setCredit(100L);
            rewardMapper.insert(user);
        }

    }

    @Test
    public void testInsert() {
        Reward reward = new Reward().setUserId(2L).setCredit(200L);

        System.out.println(reward);

        rewardMapper.insert(reward);

        System.out.println(reward);

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<Reward> userList = rewardMapper.selectList(null);
        userList.forEach(System.out::println);
    }


    @Test
    public void testSelect2() {
        QueryWrapper<Reward> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reward::getUserId, 1);

        List<Reward> users = rewardMapper.selectList(queryWrapper);

        System.out.println(users);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<Reward> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reward::getUserId, 1)   ;

        Reward user = rewardMapper.selectOne(queryWrapper);

        System.out.println(user);
    }

    @Test
    public void testQueryById() {
        Reward user = rewardMapper.queryOrderById(6L);

        System.out.println(user);
    }


    @Test
    public void testSelectPage() throws InterruptedException {
        QueryWrapper<Reward> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reward::getUserId, 1);

        System.out.println(queryWrapper.getCustomSqlSegment());
        System.out.println(queryWrapper.getSqlSegment());
        System.out.println(queryWrapper.getSqlSet());

        Page<Reward> page = new Page<>();
        page.setSize(2);
        page.setCurrent(1);

        System.out.println(page.getTotal());

        IPage<Reward> users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);


        // delete a page
        rewardMapper.deleteById(20);
        users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);

        // update a record to check the timestamp working
        QueryWrapper<Reward> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.lambda().eq(Reward::getUserId, 1)   ;
        Reward user = new Reward().setCredit(30L).setUserId(1L);

        rewardMapper.update(user, queryWrapper1);


        Thread.sleep(2000);
        users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);
    }
}

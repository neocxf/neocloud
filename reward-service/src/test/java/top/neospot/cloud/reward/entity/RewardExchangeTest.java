package top.neospot.cloud.reward.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.reward.RewardAppTest;
import top.neospot.cloud.reward.mapper.RewardExchangeMapper;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
public class RewardExchangeTest extends RewardAppTest {
    @Autowired
    private RewardExchangeMapper rewardMapper;

    @Before
    public void init() {
        QueryWrapper<RewardExchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RewardExchange::getUserId, 1)   ;

        RewardExchange user = rewardMapper.selectOne(queryWrapper);

        if (user == null) {
            user = new RewardExchange().setUserId(1L).setCredit(100L).setProductId(1L);
            rewardMapper.insert(user);
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<RewardExchange> userList = rewardMapper.selectList(null);
        userList.forEach(System.out::println);
    }


    @Test
    public void testSelect2() {
        QueryWrapper<RewardExchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RewardExchange::getUserId, 1);

        List<RewardExchange> users = rewardMapper.selectList(queryWrapper);

        System.out.println(users);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<RewardExchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RewardExchange::getUserId, 1)   ;

        RewardExchange user = rewardMapper.selectOne(queryWrapper);

        System.out.println(user);
    }


    @Test
    public void testSelectPage() throws InterruptedException {
        QueryWrapper<RewardExchange> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RewardExchange::getUserId, 1);

        System.out.println(queryWrapper.getCustomSqlSegment());
        System.out.println(queryWrapper.getSqlSegment());
        System.out.println(queryWrapper.getSqlSet());

        Page<RewardExchange> page = new Page<>();
        page.setSize(2);
        page.setCurrent(1);

        System.out.println(page.getTotal());

        IPage<RewardExchange> users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);


        // delete a page
        rewardMapper.deleteById(20);
        users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);

        // update a record to check the timestamp working
        QueryWrapper<RewardExchange> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.lambda().eq(RewardExchange::getUserId, 1)   ;
        RewardExchange user = new RewardExchange().setCredit(30L).setUserId(1L);

        rewardMapper.update(user, queryWrapper1);


        Thread.sleep(2000);
        users = rewardMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);
    }
}

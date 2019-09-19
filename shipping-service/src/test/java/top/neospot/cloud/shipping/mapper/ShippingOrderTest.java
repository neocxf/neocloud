package top.neospot.cloud.shipping.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.shipping.ShippingAppTest;
import top.neospot.cloud.shipping.entity.ShippingOrder;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/18.
 */
public class ShippingOrderTest extends ShippingAppTest {
    @Autowired
    private ShippingOrderMapper shippingMapper;

    @Before
    public void init() {
        QueryWrapper<ShippingOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShippingOrder::getRewardExchangeId, 1)   ;

        ShippingOrder user = shippingMapper.selectOne(queryWrapper);

        if (user == null) {
            user = new ShippingOrder().setRewardExchangeId(1).setType(1).setNumber(1).setProductId(1).setAddr("pudong");
            shippingMapper.insert(user);
        }

    }

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<ShippingOrder> userList = shippingMapper.selectList(null);
        userList.forEach(System.out::println);
    }


    @Test
    public void testSelect2() {
        QueryWrapper<ShippingOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShippingOrder::getRewardExchangeId, 1);

        List<ShippingOrder> users = shippingMapper.selectList(queryWrapper);

        System.out.println(users);
    }

    @Test
    public void testSelectOne() {
        QueryWrapper<ShippingOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShippingOrder::getRewardExchangeId, 1)   ;

        ShippingOrder user = shippingMapper.selectOne(queryWrapper);

        System.out.println(user);
    }


    @Test
    public void testSelectPage() throws InterruptedException {
        QueryWrapper<ShippingOrder> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShippingOrder::getRewardExchangeId, 1);

        System.out.println(queryWrapper.getCustomSqlSegment());
        System.out.println(queryWrapper.getSqlSegment());
        System.out.println(queryWrapper.getSqlSet());

        Page<ShippingOrder> page = new Page<>();
        page.setSize(2);
        page.setCurrent(1);

        System.out.println(page.getTotal());

        IPage<ShippingOrder> users = shippingMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);


        // delete a page
        shippingMapper.deleteById(20);
        users = shippingMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);

        // update a record to check the timestamp working
        QueryWrapper<ShippingOrder> queryWrapper1 = new QueryWrapper<>();
        queryWrapper.lambda().eq(ShippingOrder::getRewardExchangeId, 1)   ;
        ShippingOrder user = new ShippingOrder().setNumber(3).setRewardExchangeId(1);

        shippingMapper.update(user, queryWrapper1);


        Thread.sleep(2000);
        users = shippingMapper.selectPage(page, queryWrapper);
        users.getRecords().forEach(System.out::println);
    }
}

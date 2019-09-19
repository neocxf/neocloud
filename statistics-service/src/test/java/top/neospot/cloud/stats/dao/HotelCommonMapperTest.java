package top.neospot.cloud.stats.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.neospot.cloud.stats.ApplicationTest;
import top.neospot.cloud.stats.entity.HotelInfo;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/10/17.
 */
public class HotelCommonMapperTest extends ApplicationTest {

    @Autowired
    HotelCommonMapper hotelCommonMapper;

    @Test
    public void testFindHotelInfo() {
        HotelInfo hotelInfo = hotelCommonMapper.findHotelInfo("STARWOOD-97");

        System.out.println(hotelInfo);
    }
}

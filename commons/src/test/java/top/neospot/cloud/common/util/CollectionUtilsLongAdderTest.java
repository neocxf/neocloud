package top.neospot.cloud.common.util;

import lombok.Builder;
import lombok.Data;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/10/22.
 */
public class CollectionUtilsLongAdderTest {

    @Test
    public void list2MapTest() {
        List<Bean> beanList = Lists.newArrayList();
        beanList.add(Bean.builder().age(21).name("neo").build());
        beanList.add(Bean.builder().age(22).name("neo1").build());
        beanList.add(Bean.builder().age(23).name("neo2").build());
        beanList.add(Bean.builder().age(24).name("neo3").build());

        Map map = CollectionUtils.list2Map(beanList, "name", Bean.class);

        map.forEach((k, v) -> {
            System.out.println(k + " ------ " + v);
        });
    }

    @Data
    @Builder
    private static class Bean {
        private int age;
        private String name;
        private String email;
    }
}

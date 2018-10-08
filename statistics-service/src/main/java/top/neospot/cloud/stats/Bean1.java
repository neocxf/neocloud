package top.neospot.cloud.stats;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/9/26.
 */
@Component
public class Bean1 {

    public void printMap(Map map) {
        map.forEach((k, v) -> {
            System.out.println(k + "<--->" + v);
        });
    }
}

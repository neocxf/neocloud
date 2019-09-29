package top.neospot.cloud.inventory.controller;

import lombok.Builder;
import lombok.Data;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/29.
 */
@Data
@Builder
public class ShopInfo {
    private long id;
    private String name;
    private String level;
    private String comment;

}

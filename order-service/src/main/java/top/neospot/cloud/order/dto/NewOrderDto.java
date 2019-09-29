package top.neospot.cloud.order.dto;

import lombok.Data;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/25.
 */
@Data
public class NewOrderDto {
    private Long productId;

    private Long number;

    private Double price;

    private Long userId;
}

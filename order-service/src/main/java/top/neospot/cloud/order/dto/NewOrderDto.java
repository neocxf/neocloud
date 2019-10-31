package top.neospot.cloud.order.dto;

import lombok.Data;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/9/25.
 */
@Data
public class NewOrderDto {
    private Long userId;

    private Long addressId;

    private List<OrderLine> lines;

    @Data
    public static class OrderLine {
        private String costPerUnit;
        private int num;
        private Long productId;
        private String productName;
    }
}

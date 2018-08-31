package top.neospot.cloud.cart.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Long id;
    private Long userId;
    private List<Long> cartLines;
    private String lines;

}

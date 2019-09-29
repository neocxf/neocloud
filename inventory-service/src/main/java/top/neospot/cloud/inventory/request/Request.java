package top.neospot.cloud.inventory.request;

import lombok.Data;

import java.io.Serializable;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Data
public abstract class Request implements Serializable {
    private Long productId;

    public Request(long productId) {
        this.productId = productId;
    }

    public abstract void process();
}

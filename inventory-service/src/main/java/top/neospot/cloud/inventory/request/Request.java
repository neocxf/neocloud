package top.neospot.cloud.inventory.request;

import lombok.Data;

import java.io.Serializable;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Data
public abstract class Request implements Serializable {
    private String productCode;

    public Request(String productCode) {
        this.productCode = productCode;
    }

    public abstract void process();
}

package top.neospot.cloud.inventory.request;

import lombok.Builder;
import lombok.Data;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/23.
 */
@Data
@Builder
public class Response {
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";

    private String status;
    private String message;


}

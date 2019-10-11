package top.neospot.cloud.auth.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/10/11.
 */
@Data
@Accessors(chain = true)
public class RoleDto {
    private Long id;
    private String name;
    private String des;
    private String code;
    private String sort;
}

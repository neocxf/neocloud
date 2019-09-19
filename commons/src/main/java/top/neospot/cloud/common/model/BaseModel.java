package top.neospot.cloud.common.model;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/8/16.
 */
@Data
@Accessors(chain = true)
public abstract class BaseModel implements Serializable {
	@TableId(value = "id", type = IdType.AUTO)
	private Long id;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date lastUpdateTime;

	@TableLogic
	private Integer deleted;


}

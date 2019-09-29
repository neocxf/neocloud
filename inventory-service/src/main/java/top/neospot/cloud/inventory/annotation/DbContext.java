package top.neospot.cloud.inventory.annotation;

import java.lang.annotation.*;

/**
 * By neo.chen{neocxf@gmail.com} on 2019/5/21.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DbContext {
    DbEnum value() default DbEnum.MASTER;
}


package com.asoulfan.platform.common.nacos;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author asuka
 * @date 2021/11/06
 */
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NacosValue {
    String dataId();

    String group() default "DEFAULT_GROUP";
}

package com.lei.smart.config;
/**
 * 自定义事务注解
 * value为String类型数组，传入为定义的事务管理器
 * @author 一dingjianlei@xdf.cn
 *
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
public @interface CustomTransaction {
    String[] value() default {};
}

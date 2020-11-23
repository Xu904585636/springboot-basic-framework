package com.xu.config.aspect;


import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {
    /**
     * 描述
     */
    String description() default "";
    /**
     * 方法类型
     */
    String methodType() default "";
}

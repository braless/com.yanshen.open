package com.yanshen.common.redis.component;

import java.lang.annotation.*;

/**
 * 防止重复提交
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoRepeatSubmit {

    /**
     * 锁过期的时间
     */
    int seconds() default 3;

    /**
     * 锁过期的最大时间
     *
     * @return
     */
    int maxSeconds() default 20;

    /**
     * 锁的位置
     */
    String location() default "NoRepeatSubmit";

    /**
     * 要扫描的参数位置
     */
    int argIndex() default 0;

    /**
     * 参数名称
     */
    String name() default "";

    /**
     * 重复提交的提示语
     *
     * @return
     */
    String tip() default "操作过于频繁，请稍后重试";
}

package com.kuangstudy.aop.limiter;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AcessLimter {
    // 每timeout限制请求的个数
    int limit() default 5;

    // 时间，单位默认是秒
    int timeout() default 1;
}

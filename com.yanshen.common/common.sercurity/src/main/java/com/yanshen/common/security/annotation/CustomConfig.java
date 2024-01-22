package com.yanshen.common.security.annotation;

import com.yanshen.common.security.config.ApplicationConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.annotation.*;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 10:59
 * @Description:
 * @Location: com.yanshen.common.security.annotation
 * @Project: com.yanshen.open
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
// 表示通过aop框架暴露该代理对象,AopContext能够访问
//@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan({"com.yanshen.*.mapper", "com.yanshen.**.mapper", "com.yanshen.***.mapper", "com.yanshen.****.mapper"})
// 开启线程异步执行
@EnableAsync
@Import({ApplicationConfig.class, FeignAutoConfiguration.class})
public @interface CustomConfig {
}

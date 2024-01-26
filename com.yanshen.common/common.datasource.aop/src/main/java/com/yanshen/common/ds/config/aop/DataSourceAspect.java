package com.yanshen.common.ds.config.aop;


import com.yanshen.common.ds.config.DataSource;
import com.yanshen.common.ds.config.DataSourceType;
import com.yanshen.common.ds.config.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:02
 * @Description: https://cloud.tencent.com/developer/article/2334201
 * @Location: com.yanshen.common.datasource.aop
 * @Project: com.yanshen.open
 */
@Aspect
@Component
@Slf4j
public class DataSourceAspect {

    @Around("@annotation(com.yanshen.common.ds.config.DataSource) || @within(com.yanshen.common.ds.config.DataSource)")
    public Object switchDataSource(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        DataSource dataSource = signature.getMethod().getAnnotation(DataSource.class);
        if (dataSource == null) {
            Class<?> declaringType = signature.getDeclaringType();
            dataSource = declaringType.getAnnotation(DataSource.class);
        }
        if (dataSource == null) {
            log.info("匹配到数据源：{}", dataSource.value());
            DynamicDataSource.setDataSource(DataSourceType.MASTER.getValue());
        } else {
            log.info("匹配到数据源：{}", dataSource.value());
            DynamicDataSource.setDataSource(dataSource.value().getValue());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
        }
    }
//    @Pointcut("execution(* com.yanshen.hotel.service..*.*(..))")
//    private void dsPointCut() {
//    }
//
//    @Around("dsPointCut()")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        // 获取当前指定的数据源
//        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
//        Method method = ms.getMethod();
//        DataSource dataSource = method.getAnnotation(DataSource.class);
//
//        if (Objects.isNull(dataSource)) {
//            // 使用默认数据源
//            DynamicDataSource.setDataSource("master");
//            log.info("未匹配到数据源，使用默认数据源");
//        } else {
//            // 匹配到的话，设置到动态数据源上下文中
//            DynamicDataSource.setDataSource(dataSource.value().getValue());
//            log.info("匹配到数据源：{}", dataSource.value().getValue());
//        }
//
//        try {
//            // 执行目标方法，返回值即为当前方法的返回值
//            return joinPoint.proceed();
//        } finally {
//            // 方法执行完毕之后，销毁当前数据源信息，进行垃圾回收
//            DynamicDataSource.clearDataSource();
//            log.info("当前数据源已清空");
//        }
//    }
}

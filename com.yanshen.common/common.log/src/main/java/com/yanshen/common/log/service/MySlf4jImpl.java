package com.yanshen.common.log.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;

/**
 * @author: baohao-jia
 * @date: 2023年08月09日 17:08
 * @ClassName: MySlf4jImplConfig
 * @Description: 重写mybitas日志打印，将sql打印日志级别强制调整为info级别，方便写入info日志中
 */
@Slf4j
public class MySlf4jImpl implements Log {

    public MySlf4jImpl(String clazz) {
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return true;
    }

    @Override
    public void error(String s, Throwable e) {
        log.error(s);
        e.printStackTrace(System.err);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void debug(String s) {
        log.info(s);
    }

    @Override
    public void trace(String s) {
        log.trace(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }
}

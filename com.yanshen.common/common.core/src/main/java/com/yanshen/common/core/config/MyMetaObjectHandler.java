package com.yanshen.common.core.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import com.yanshen.common.core.context.SecurityContextHolder;
import com.yanshen.common.core.util.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("createBy", StringUtils.isNotBlank(SecurityContextHolder.getUserName()) ? SecurityContextHolder.getUserName() : "", metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.strictInsertFill(metaObject, "delFlag", Integer.class, 0);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", StringUtils.isNotBlank(SecurityContextHolder.getUserName()) ? SecurityContextHolder.getUserName() : "", metaObject);
    }
}

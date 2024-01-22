package com.yanshen.messager.api.factory;

import com.yanshen.messager.api.RemoteMessagerService;

import com.yanshen.common.core.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:13
 * @Description:
 * @Location: com.yanshen.auth.api.factory
 * @Project: com.yanshen.open
 */
@Component
@Slf4j
public class MessagerFallbackFactory implements FallbackFactory<RemoteMessagerService> {
    @Override
    public RemoteMessagerService create(Throwable cause) {
        return new RemoteMessagerService() {
            @Override
            public R<Boolean> sendMessage(String userName, String password) {
                return R.fail("服务调用失败!");
            }

        };
    }
}

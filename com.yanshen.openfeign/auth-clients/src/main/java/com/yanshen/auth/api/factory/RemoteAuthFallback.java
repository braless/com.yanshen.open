package com.yanshen.auth.api.factory;

import com.yanshen.auth.api.RemoteAuthClient;
import com.yanshen.common.core.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 10:22
 * @Description:
 * @Location: com.yanshen.auth.api.factory
 * @Project: com.yanshen.open
 */
@Slf4j
@Component
public class RemoteAuthFallback implements FallbackFactory<RemoteAuthClient> {
    @Override
    public RemoteAuthClient create(Throwable cause) {
        return new RemoteAuthClient() {
            @Override
            public R auth(String userName, String password) {
                return R.fail("服务调用失败");
            }
        };
    }
}

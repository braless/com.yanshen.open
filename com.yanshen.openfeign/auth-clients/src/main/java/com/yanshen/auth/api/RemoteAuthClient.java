package com.yanshen.auth.api;

import com.yanshen.auth.api.factory.RemoteAuthFallback;
import com.yanshen.common.core.constants.ServiceNameConstants;
import com.yanshen.common.core.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-19 10:18
 * @Description:
 * @Location: com.yanshen.auth.api
 * @Project: com.yanshen.open
 */

@FeignClient(value = ServiceNameConstants.AUTH_SERVICE,fallbackFactory = RemoteAuthFallback.class)
public interface RemoteAuthClient {

    @RequestMapping("/sys/auth/do")
    public R auth(String userName, String password);
}

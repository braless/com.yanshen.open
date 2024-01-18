package com.yanshen.auth.api;

import com.yanshen.auth.api.factory.AuthFallbackFactory;
import com.yanshen.core.constants.ServiceNameConstants;


/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:11
 * @Description:
 * @Location: com.yanshen.auth.api
 * @Project: com.yanshen.open
 */
@FeignClient(contextId = "remoteBidCallService", value = ServiceNameConstants.CHANNEL_SERVICE, fallbackFactory = AuthFallbackFactory.class)
public interface AuthService {
}

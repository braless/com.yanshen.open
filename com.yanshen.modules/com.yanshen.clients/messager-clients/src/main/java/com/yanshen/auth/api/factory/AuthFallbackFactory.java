package com.yanshen.auth.api.factory;

import com.yanshen.auth.api.AuthService;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:13
 * @Description:
 * @Location: com.yanshen.auth.api.factory
 * @Project: com.yanshen.open
 */
public class AuthFallbackFactory implements FallbackFactory<AuthService> {
}

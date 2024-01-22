package com.yanshen.hotel.feign;

import com.yanshen.common.core.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-22 15:46
 * @Description:
 * @Location: com.yanshen.hotel.feign
 * @Project: com.yanshen.open
 */
@FeignClient(name = "com.yanshen.auth")
public interface AuthClient {
    @RequestMapping("/sys/auth/do")
    R<Boolean> auth(@RequestParam String userName,@RequestParam String password);
}

package com.yanshen.messager.api;

import com.yanshen.messager.api.factory.MessagerFallbackFactory;
import com.yanshen.common.core.constants.ServiceNameConstants;
import com.yanshen.common.core.response.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:11
 * @Description:
 * @Location: com.yanshen.auth.api
 * @Project: com.yanshen.open
 */
@FeignClient(contextId = "RemoteMessagerService", value = ServiceNameConstants.MESSAGER_SERVICE, fallbackFactory = MessagerFallbackFactory.class)
public interface RemoteMessagerService {

    @RequestMapping("/msg/send")
    R<Boolean> sendMessage(@RequestParam String userName, @RequestParam String password);
}

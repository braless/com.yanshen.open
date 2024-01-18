package com.yanshen.auth.controller;

import com.yanshen.core.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 16:02
 * @Description:
 * @Location: com.yanshen.auth.controller
 * @Project: com.yanshen.open
 */
@RestController
@RequestMapping("/sys/auth")
@Slf4j
public class AuthController {


    @RequestMapping("/do")
    public R<?> auth(@RequestParam String userName, @RequestParam String password) {
        log.info("尝试登录:{},{}",userName,password);
        return R.ok("this is ok");
    }
}

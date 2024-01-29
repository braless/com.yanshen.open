package com.yanshen.auth.controller;

import com.yanshen.auth.domain.BackendBody;
import com.yanshen.auth.service.LoginService;
import com.yanshen.common.core.response.R;
import com.yanshen.common.security.domain.LoginUser;
import com.yanshen.common.security.service.TokenService;
import com.yanshen.messager.api.RemoteMessagerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    private RemoteMessagerService remoteMessagerService;


    @Autowired
    private LoginService loginService;

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/login")
    public R<?> auth(@RequestBody BackendBody loginBody) {
        LoginUser loginUser =new LoginUser();
        log.info("尝试登录:{},{}", loginBody.getUsername(), loginBody.getPassword());
        LoginUser login = loginService.login(loginBody);
        Map<String,Object> token = tokenService.createToken(login);
        return R.ok(token);
    }
}

package com.yanshen.auth.service;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.yanshen.admin.clients.model.LoginUser;
import com.yanshen.auth.domain.BackendBody;
import com.yanshen.auth.domain.OauthClientInfo;
import com.yanshen.common.core.constants.CacheConstants;
import com.yanshen.common.core.constants.Constants;
import com.yanshen.common.core.constants.SecurityConstants;
import com.yanshen.common.core.exception.ServiceException;
import com.yanshen.common.core.util.StringUtils;
import com.yanshen.common.redis.service.RedisService;
import com.yanshen.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-23 14:19
 * @Description:
 * @Location: com.yanshen.auth.service
 * @Project: com.yanshen.open
 */
@Service
public class LoginService {

    @Autowired
    private RedisService redisService;



    /**
     * 后台登录
     * @param loginBody
     * @return
     */
    public LoginUser login(BackendBody loginBody) {
        String clientId = loginBody.getClientId();
        String clientSecret = loginBody.getClientSecret();
        String grantType = loginBody.getGrantType();
        if (StrUtil.isAllBlank(clientId, clientSecret)) {
            redisService.setCacheObject(StrUtil.format("{}{}", CacheConstants.LOGIN_FAIL, clientId), 1, 60 * 60 * 24L);
            throw new ServiceException("客户端ID或客户端密钥不能为空");
        }
        if (StrUtil.isBlank(grantType)) {
            //保存记录
            throw new ServiceException("授权类型为必传！");
        }
        //获取登录客户端
        redisService.setCacheObject(StrUtil.format("{}{}", CacheConstants.AUTH_CLIENT, clientId), JSON.toJSONString(loginBody));
        OauthClientInfo clientInfo = new OauthClientInfo();
        clientInfo.setClientSecret("cyc@123456");
        clientInfo.setAuthorizedGrantTypes("password,client");
        clientInfo.setBusinessType("SYS_USER");
        Object cacheObject = redisService.getCacheObject(StrUtil.format("{}{}", CacheConstants.AUTH_CLIENT, clientId));
        if(ObjUtil.isEmpty(clientInfo)){
            throw new ServiceException("客户端不存在");
        }
        if(!clientSecret.equals(clientInfo.getClientSecret())){
            throw new ServiceException("客户端密钥不正确");
        }
        if(!clientInfo.getAuthorizedGrantTypes().contains(grantType)){
            throw new ServiceException("客户端没有此登录权限");
        }
        //客户端被删除
        //校验客户端ID以及密钥
        if (!SecurityUtils.matchesPassword(clientSecret, clientInfo.getClientSecret())) {
            String errMsg = "登录密钥不正确！";
            //throw new ServiceException(errMsg);
        }
        //校验登录客户端是否有登录类型权限
        if (!clientInfo.getAuthorizedGrantTypes().contains(loginBody.getGrantType())) {
            String errMsg = "您没有此登录权限！";
            throw new ServiceException(errMsg);
        }
        LoginUser loginUser = null;
        //授权模式判定
        if (grantType.equals(SecurityConstants.PASSWORD)) {
            //密码模式
            loginUser = passwordLogin(loginBody, clientInfo.getBusinessType());
        } else if (grantType.equals(SecurityConstants.CLIENT)) {
            //客户端模式
            loginUser = clientLogin(loginBody);
        } else if (grantType.equals(SecurityConstants.CODE)) {
            //授权码模式
            loginUser = codeLogin(clientInfo, clientInfo.getSource());
        } else {
            String errMsg = "不支持的授权类型！";
            //recordLogService.recordLogininfor(clientId, Constants.LOGIN_FAIL, errMsg);
            throw new RuntimeException(errMsg);
        }
        return loginUser;
    }
    /**
     * 密码模式登录
     */
    private LoginUser passwordLogin(BackendBody form, String businessType) {
        String username = form.getUsername();
        String password = form.getPassword();
        // 登录业务类型为空 错误
        if (StringUtils.isBlank(businessType)) {
            //recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "登录类型未录入！");
            throw new RuntimeException("登录类型未录入！");
        }
        // 用户名为空 错误
        // 登录业务类型为空 错误
        // 根据登录类型查询用户信息
        LoginUser loginUser=new LoginUser();
        loginUser.setResourceIds("messager,auth");
        return loginUser;
    }
    /**
     * 客户端模式登录
     *
     * @param form
     */
    private LoginUser clientLogin(BackendBody form) {
        return new LoginUser();
    }

    /**
     * 授权码模式登录
     */
    private LoginUser codeLogin(OauthClientInfo info, String source) {
        return null;

    }

}

package com.yanshen.common.security.service;

import com.yanshen.admin.clients.model.LoginUser;
import com.yanshen.common.core.constants.CacheConstants;
import com.yanshen.common.core.constants.SecurityConstants;
import com.yanshen.common.core.util.*;
import com.yanshen.common.redis.service.RedisService;
import com.yanshen.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-23 14:02
 * @Description:
 * @Location: com.yanshen.common.security.service
 * @Project: com.yanshen.open
 */
@Component
public class TokenService {
    protected static final long MILLIS_SECOND = 1000;
    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;
    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;
    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;
    @Autowired
    private RedisService redisService;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {

        String token = IdUtils.fastUUID();
        String userId = null;
        String userName = null;
        String memberId = null;
        String operatorId = null;

        //系统用户
        if (loginUser.getSysUser() != null) {
            userId = loginUser.getSysUser().getUserId().toString();
            userName = loginUser.getSysUser().getUserName();
        }

/*
        //门户用户
        if (loginUser.getMember() != null) {
            memberId = loginUser.getMember().getMemberId();
        }

        //小程序用户
        if (loginUser.getOperator() != null) {
            operatorId = loginUser.getOperator().getId();
        }
*/

        loginUser.setToken(token);
        loginUser.setUserid(userId);
        loginUser.setUsername(userName);
        loginUser.setMemberId(memberId);
        loginUser.setOperatorId(operatorId);
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);
        claimsMap.put(SecurityConstants.DETAILS_MEMBER_ID, memberId);
        claimsMap.put(SecurityConstants.DETAILS_OPERATOR_ID, operatorId);
        claimsMap.put(SecurityConstants.DETAILS_CUSTOMER_ID, loginUser.getCustomerId());
        claimsMap.put(SecurityConstants.DETAILS_ORG_ID, loginUser.getOrgId());
        claimsMap.put(SecurityConstants.RESOURCE_IDS, loginUser.getResourceIds());
        claimsMap.put(SecurityConstants.DETAILS_PLATFORM_CODE, loginUser.getPlatformCode());
        claimsMap.put(SecurityConstants.SCOPE, loginUser.getScope());
        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        return rspMap;
    }


    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token) {
        LoginUser user = null;
        try {
            if (StringUtils.isNotEmpty(token)) {
                String userkey = JwtUtils.getUserKey(token);
                user = redisService.getCacheObject(getTokenKey(userkey));
                return user;
            }
        } catch (Exception e) {
        }
        return user;
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUser loginUser) {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}

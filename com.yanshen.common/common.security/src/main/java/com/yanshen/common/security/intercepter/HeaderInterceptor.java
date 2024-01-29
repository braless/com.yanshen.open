package com.yanshen.common.security.intercepter;


import com.yanshen.common.core.constants.SecurityConstants;
import com.yanshen.common.core.context.SecurityContextHolder;
import com.yanshen.common.core.util.ServletUtils;
import com.yanshen.common.core.util.StringUtils;
import com.yanshen.common.security.auth.AuthUtil;
import com.yanshen.common.security.domain.LoginUser;
import com.yanshen.common.security.utils.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
 * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
 *
 * @author zksk
 */
public class HeaderInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
        SecurityContextHolder.setMemberId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_MEMBER_ID));
        SecurityContextHolder.setOperatorId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_OPERATOR_ID));
        SecurityContextHolder.setCustomerId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_CUSTOMER_ID));
        SecurityContextHolder.setPlatformCode(ServletUtils.getHeader(request, SecurityConstants.DETAILS_PLATFORM_CODE));
        SecurityContextHolder.setOrgId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_ORG_ID));
        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));

        String token = SecurityUtils.getToken();
        if (StringUtils.isNotEmpty(token)) {
            LoginUser loginUser = AuthUtil.getLoginUser(token);
            if (StringUtils.isNotNull(loginUser)) {
                AuthUtil.verifyLoginUserExpire(loginUser);
                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        SecurityContextHolder.remove();
    }
}

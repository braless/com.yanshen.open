package com.yanshen.common.security.utils;
import com.yanshen.common.core.constants.SecurityConstants;
import com.yanshen.common.core.constants.TokenConstants;
import com.yanshen.common.core.context.SecurityContextHolder;
import com.yanshen.common.core.text.Convert;
import com.yanshen.common.core.util.ServletUtils;
import com.yanshen.common.core.util.StringUtils;
import com.yanshen.common.security.domain.LoginUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.servlet.http.HttpServletRequest;

/**
 * 权限获取工具类
 *
 * @author Yanshen
 */
public class SecurityUtils {
    /**
     * 获取用户ID
     */
    public static Long getUserId() {
        return Convert.toLong(SecurityContextHolder.getUserId(), 0L);
    }

    /**
     * 获取企业成员ID
     */
    public static String getMemberId() {
        return SecurityContextHolder.getMemberId();
    }

    /**
     * 获取联系人ID
     *
     * @return
     */
    public static String getOperatorId() {
        return SecurityContextHolder.getOperatorId();
    }

    /**
     * 获取客户ID
     *
     * @return
     */
    public static String getCustomerId() {
        return SecurityContextHolder.getCustomerId();
    }

    public static String getPlatformCode() {
        return SecurityContextHolder.getPlatformCode();
    }

    /**
     * 获取机构ID
     *
     * @return
     */
    public static String getOrgId() {
        return SecurityContextHolder.getOrgId();
    }

    /**
     * 获取用户名称
     */
    public static String getUsername() {
        return SecurityContextHolder.getUserName();
    }

    /**
     * 获取用户key
     */
    public static String getUserKey() {
        return SecurityContextHolder.getUserKey();
    }

    /**
     * 获取登录用户信息
     */
    public static LoginUser getLoginUser() {
        return SecurityContextHolder.get(SecurityConstants.LOGIN_USER, LoginUser.class);
    }

    /**
     * 获取请求token
     */
    public static String getToken() {
        return getToken(ServletUtils.getRequest());
    }

    /**
     * 根据request获取请求token
     */
    public static String getToken(HttpServletRequest request) {
        // 从header获取token标识
        String token = request.getHeader(TokenConstants.AUTHENTICATION);
        return replaceTokenPrefix(token);
    }

    /**
     * 裁剪token前缀
     */
    public static String replaceTokenPrefix(String token) {
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(TokenConstants.PREFIX)) {
            token = token.replaceFirst(TokenConstants.PREFIX, "");
        }
        return token;
    }

    /**
     * 是否为管理员
     *
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    /**
     * 判断密码是否相同
     *
     * @param rawPassword     真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

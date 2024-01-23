package com.yanshen.common.core.util;


import com.yanshen.common.core.constants.SecurityConstants;
import com.yanshen.common.core.constants.TokenConstants;

import com.yanshen.common.core.text.Convert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Map;

/**
 * Jwt工具类
 *
 * @author zksk
 */
public class JwtUtils {
    public static String secret = TokenConstants.SECRET;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    public static String createToken(Map<String, Object> claims) {
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
        return token;
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    public static Claims parseToken(String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserKey(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户标识
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserKey(Claims claims) {
        return getValue(claims, SecurityConstants.USER_KEY);
    }

    /**
     * 根据令牌获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public static String getUserId(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取企业用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getMemberId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_MEMBER_ID);
    }

    /**
     * 根据身份信息获取用户ID
     *
     * @param claims 身份信息
     * @return 用户ID
     */
    public static String getUserId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USER_ID);
    }

    /**
     * 根据令牌获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public static String getUserName(String token) {
        Claims claims = parseToken(token);
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取用户名
     *
     * @param claims 身份信息
     * @return 用户名
     */
    public static String getUserName(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_USERNAME);
    }

    /**
     * 根据身份信息获取能访问的资源id集合
     *
     * @param claims 身份信息
     * @return 资源id集合(字符串)
     */
    public static String getResourceIds(Claims claims) {
        return getValue(claims, SecurityConstants.RESOURCE_IDS);
    }

    /**
     * 根据身份信息获取能访问的权限范围
     *
     * @param claims 身份信息
     * @return 权限范围(字符串)
     */
    public static String getScope(Claims claims) {
        return getValue(claims, SecurityConstants.SCOPE);
    }

    /**
     * 根据身份信息获取键值
     *
     * @param claims 身份信息
     * @param key    键
     * @return 值
     */
    public static String getValue(Claims claims, String key) {
        return Convert.toStr(claims.get(key), "");
    }

    /**
     * 根据身份信息获取联系人ID
     *
     * @param claims
     * @return
     */
    public static String getOperatorId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_OPERATOR_ID);
    }

    /**
     * 根据身份信息获取客户ID
     *
     * @param claims
     * @return
     */
    public static String getCustomerId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_CUSTOMER_ID);
    }

    /**
     * 根据身份信息获取机构ID
     *
     * @param claims
     * @return
     */
    public static String getOrgId(Claims claims) {
        return getValue(claims, SecurityConstants.DETAILS_ORG_ID);
    }

}

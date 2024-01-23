package com.yanshen.common.core.constants;

import java.security.MessageDigest;

/**
 * 权限相关通用常量
 *
 * @author Yanshen
 */
public class SecurityConstants {
    /**
     * 用户ID字段
     */
    public static final String DETAILS_USER_ID = "user_id";

    /**
     * 用户名字段
     */
    public static final String DETAILS_USERNAME = "username";

    /**
     * 企业ID字段
     */
    public static final String DETAILS_MEMBER_ID = "member_id";

    /**
     * 联系人ID字段
     */
    public static final String DETAILS_OPERATOR_ID = "operator_id";

    /**
     * 对应的内部客户ID（客户第三方登录使用该字段）
     */
    public static final String DETAILS_CUSTOMER_ID = "customer_id";

    public static final String DETAILS_PLATFORM_CODE = "platform_code";

    /**
     * 对应的内部机构ID（机构第三方登录使用该字段）
     */
    public static final String DETAILS_ORG_ID = "org_id";


    /**
     * 授权信息字段
     */
    public static final String AUTHORIZATION_HEADER = "authorization";

    /**
     * 请求来源
     */
    public static final String FROM_SOURCE = "from-source";

    /**
     * 内部请求
     */
    public static final String INNER = "inner";

    /**
     * 用户标识
     */
    public static final String USER_KEY = "user_key";

    /**
     * 登录用户
     */
    public static final String LOGIN_USER = "login_user";

    /**
     * 角色权限
     */
    public static final String ROLE_PERMISSION = "role_permission";

    /**
     * 客户端授权资源
     */
    public static final String RESOURCE_IDS = "resource_ids";

    /**
     * 客户端权限范围
     */
    public static final String SCOPE = "scope";

    /**
     * 授权类型
     */
    public static final String PASSWORD = "password"; // 密码模式

    public static final String CODE = "code"; // 授权码模式

    public static final String CLIENT = "client"; // 客户端模式

    /**
     * CA登录的用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "63f45a5e028776e6";


    /**
     * 生成md5
     *
     * @param args
     */
    public static void main(String[] args) {

//        String password = "CA_USER_DEFAULT_PASSWORD";
        String password = "FUQIAO_DEFAULT_PASSWORD";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(password.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32位: " + buf.toString());// 32位的加密
//            System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}

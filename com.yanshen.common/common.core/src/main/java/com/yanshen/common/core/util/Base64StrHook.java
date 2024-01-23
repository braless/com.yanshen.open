package com.yanshen.common.core.util;

/**
 * @author: baohao-jia
 * @date: 2023年05月06日 15:14
 * @ClassName: Base64StrHook
 * @Description: base64编码字符串拦截替换
 */
public class Base64StrHook {

    public static final String PATTERN = "\"[\\w+-=\\\\]{4096,}?\"";
    //"\"[\\w+-=]{1024,}?\"";
    public static final String REPLACE = "\"very long (more than 4096)\"";

    public static String hookStr(String param) {
        if (StringUtils.isEmpty(param)) {
            return null;
        }
        return param.replaceAll(PATTERN, REPLACE);
    }
}

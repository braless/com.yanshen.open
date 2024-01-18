//package util;
//
//import cn.hutool.http.HttpUtil;
//import cn.hutool.json.JSONObject;
//import cn.hutool.json.JSONUtil;
//import com.xrw.common.constant.Constants;
//import com.xrw.common.text.StrFormatter;
//import lombok.extern.slf4j.Slf4j;
//import nl.basjes.parse.useragent.UserAgent;
//import nl.basjes.parse.useragent.UserAgentAnalyzer;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.http.HttpServletRequest;
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.UnknownHostException;
//import java.util.*;
//
///**
// * @author luojc
// * 字符串工具类, 继承org.apache.commons.lang3.StringUtils类
// */
//@Slf4j
//public class StringUtils extends org.apache.commons.lang3.StringUtils {
//
//    private static final char SEPARATOR = '_';
//
//    private static final String UNKNOWN = "unknown";
//
//    /**
//     * 空字符串
//     */
//    private static final String NULLSTR = "";
//
//    /**
//     * 注入bean
//     */
////    private final static Ip2regionSearcher IP_SEARCHER = SpringContextHolder.getBean(Ip2regionSearcher.class);
//
//
//    private static final UserAgentAnalyzer USER_AGENT_ANALYZER = UserAgentAnalyzer
//            .newBuilder()
//            .hideMatcherLoadStats()
//            .withCache(10000)
//            .withField(UserAgent.AGENT_NAME_VERSION)
//            .build();
//
////    /**
////     * 驼峰命名法工具
////     *
////     * @return toCamelCase(" hello_world ") == "helloWorld"
////     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
////     * toUnderScoreCase("helloWorld") = "hello_world"
////     */
////    public static String toCamelCase(String s) {
////        if (s == null) {
////            return null;
////        }
////
////        s = s.toLowerCase();
////
////        StringBuilder sb = new StringBuilder(s.length());
////        boolean upperCase = false;
////        for (int i = 0; i < s.length(); i++) {
////            char c = s.charAt(i);
////
////            if (c == SEPARATOR) {
////                upperCase = true;
////            } else if (upperCase) {
////                sb.append(Character.toUpperCase(c));
////                upperCase = false;
////            } else {
////                sb.append(c);
////            }
////        }
////
////        return sb.toString();
////    }
//
//    /**
//     * 驼峰命名法工具
//     *
//     * @return toCamelCase(" hello_world ") == "helloWorld"
//     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
//     * toUnderScoreCase("helloWorld") = "hello_world"
//     */
//    public static String toCapitalizeCamelCase(String s) {
//        if (s == null) {
//            return null;
//        }
//        s = toCamelCase(s);
//        return s.substring(0, 1).toUpperCase() + s.substring(1);
//    }
//
//    /**
//     * 驼峰命名法工具
//     *
//     * @return toCamelCase(" hello_world ") == "helloWorld"
//     * toCapitalizeCamelCase("hello_world") == "HelloWorld"
//     * toUnderScoreCase("helloWorld") = "hello_world"
//     */
////    static String toUnderScoreCase(String s) {
////        if (s == null) {
////            return null;
////        }
////
////        StringBuilder sb = new StringBuilder();
////        boolean upperCase = false;
////        for (int i = 0; i < s.length(); i++) {
////            char c = s.charAt(i);
////
////            boolean nextUpperCase = true;
////
////            if (i < (s.length() - 1)) {
////                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
////            }
////
////            if ((i > 0) && Character.isUpperCase(c)) {
////                if (!upperCase || !nextUpperCase) {
////                    sb.append(SEPARATOR);
////                }
////                upperCase = true;
////            } else {
////                upperCase = false;
////            }
////
////            sb.append(Character.toLowerCase(c));
////        }
////
////        return sb.toString();
////    }
//
//    /**
//     * 获取ip地址
//     */
//    public static String getIp(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        String comma = ",";
//        String localhost = "127.0.0.1";
//        if (ip.contains(comma)) {
//            ip = ip.split(",")[0];
//        }
//        if (localhost.equals(ip)) {
//            // 获取本机真正的ip地址
//            try {
//                ip = InetAddress.getLocalHost().getHostAddress();
//            } catch (UnknownHostException e) {
//                log.error(e.getMessage(), e);
//            }
//        }
//        return ip;
//    }
//
//    /**
//     * 根据ip获取详细地址
//     */
//    public static String getCityInfo(String ip) {
////        if (ElAdminProperties.ipLocal) {
////            return getLocalCityInfo(ip);
////        } else {
////            return getHttpCityInfo(ip);
////        }
//        return "127.0.0.1";
//    }
//
//    /**
//     * 根据ip获取详细地址
//     */
//    public static String getHttpCityInfo(String ip) {
//        String api = String.format(ElAdminConstant.Url.IP_URL, ip);
//        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
//        return object.get("addr", String.class);
//    }
//
//    /**
//     * 根据ip获取详细地址
//     */
////    public static String getLocalCityInfo(String ip) {
////        IpInfo ipInfo = IP_SEARCHER.memorySearch(ip);
////        if (ipInfo != null) {
////            return ipInfo.getAddress();
////        }
////        return null;
////
////    }
//
//    public static String getBrowser(HttpServletRequest request) {
//        UserAgent.ImmutableUserAgent userAgent = USER_AGENT_ANALYZER.parse(request.getHeader("User-Agent"));
//        return userAgent.get(UserAgent.AGENT_NAME_VERSION).getValue();
//    }
//
//    /**
//     * 获得当天是周几
//     */
//    public static String getWeekDay() {
//        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(new Date());
//
//        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
//        if (w < 0) {
//            w = 0;
//        }
//        return weekDays[w];
//    }
//
//    /**
//     * 获取当前机器的IP
//     *
//     * @return /
//     */
//    public static String getLocalIp() {
//        try {
//            InetAddress candidateAddress = null;
//            // 遍历所有的网络接口
//            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
//                NetworkInterface anInterface = interfaces.nextElement();
//                // 在所有的接口下再遍历IP
//                for (Enumeration<InetAddress> inetAddresses = anInterface.getInetAddresses(); inetAddresses.hasMoreElements(); ) {
//                    InetAddress inetAddr = inetAddresses.nextElement();
//                    // 排除loopback类型地址
//                    if (!inetAddr.isLoopbackAddress()) {
//                        if (inetAddr.isSiteLocalAddress()) {
//                            // 如果是site-local地址，就是它了
//                            return inetAddr.getHostAddress();
//                        } else if (candidateAddress == null) {
//                            // site-local类型的地址未被发现，先记录候选地址
//                            candidateAddress = inetAddr;
//                        }
//                    }
//                }
//            }
//            if (candidateAddress != null) {
//                return candidateAddress.getHostAddress();
//            }
//            // 如果没有发现 non-loopback地址.只能用最次选的方案
//            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
//            if (jdkSuppliedAddress == null) {
//                return "";
//            }
//            return jdkSuppliedAddress.getHostAddress();
//        } catch (Exception e) {
//            return "";
//        }
//    }
//
//
//    /**
//     * 获取参数不为空值
//     *
//     * @param value defaultValue 要判断的value
//     * @return value 返回值
//     */
//    public static <T> T nvl(T value, T defaultValue) {
//        return value != null ? value : defaultValue;
//    }
//
//    /**
//     * * 判断一个Collection是否为空， 包含List，Set，Queue
//     *
//     * @param coll 要判断的Collection
//     * @return true：为空 false：非空
//     */
//    public static boolean isEmpty(Collection<?> coll) {
//        return isNull(coll) || coll.isEmpty();
//    }
//
//    /**
//     * * 判断一个Collection是否非空，包含List，Set，Queue
//     *
//     * @param coll 要判断的Collection
//     * @return true：非空 false：空
//     */
//    public static boolean isNotEmpty(Collection<?> coll) {
//        return !isEmpty(coll);
//    }
//
//    /**
//     * * 判断一个对象数组是否为空
//     *
//     * @param objects 要判断的对象数组
//     *                * @return true：为空 false：非空
//     */
//    public static boolean isEmpty(Object[] objects) {
//        return isNull(objects) || (objects.length == 0);
//    }
//
//    /**
//     * * 判断一个对象数组是否非空
//     *
//     * @param objects 要判断的对象数组
//     * @return true：非空 false：空
//     */
//    public static boolean isNotEmpty(Object[] objects) {
//        return !isEmpty(objects);
//    }
//
//    /**
//     * * 判断一个Map是否为空
//     *
//     * @param map 要判断的Map
//     * @return true：为空 false：非空
//     */
//    public static boolean isEmpty(Map<?, ?> map) {
//        return isNull(map) || map.isEmpty();
//    }
//
//    /**
//     * * 判断一个Map是否为空
//     *
//     * @param map 要判断的Map
//     * @return true：非空 false：空
//     */
//    public static boolean isNotEmpty(Map<?, ?> map) {
//        return !isEmpty(map);
//    }
//
//    /**
//     * * 判断一个字符串是否为空串
//     *
//     * @param str String
//     * @return true：为空 false：非空
//     */
//    public static boolean isEmpty(String str) {
//        return isNull(str) || NULLSTR.equals(str.trim());
//    }
//
//    /**
//     * * 判断一个字符串是否为非空串
//     *
//     * @param str String
//     * @return true：非空串 false：空串
//     */
//    public static boolean isNotEmpty(String str) {
//        return !isEmpty(str);
//    }
//
//    /**
//     * * 判断一个对象是否为空
//     *
//     * @param object Object
//     * @return true：为空 false：非空
//     */
//    public static boolean isNull(Object object) {
//        return object == null;
//    }
//
//    /**
//     * * 判断一个对象是否非空
//     *
//     * @param object Object
//     * @return true：非空 false：空
//     */
//    public static boolean isNotNull(Object object) {
//        return !isNull(object);
//    }
//
//    /**
//     * * 判断一个对象是否是数组类型（Java基本型别的数组）
//     *
//     * @param object 对象
//     * @return true：是数组 false：不是数组
//     */
//    public static boolean isArray(Object object) {
//        return isNotNull(object) && object.getClass().isArray();
//    }
//
//    /**
//     * 去空格
//     */
//    public static String trim(String str) {
//        return (str == null ? "" : str.trim());
//    }
//
//    /**
//     * 截取字符串
//     *
//     * @param str   字符串
//     * @param start 开始
//     * @return 结果
//     */
//    public static String substring(final String str, int start) {
//        if (str == null) {
//            return NULLSTR;
//        }
//
//        if (start < 0) {
//            start = str.length() + start;
//        }
//
//        if (start < 0) {
//            start = 0;
//        }
//        if (start > str.length()) {
//            return NULLSTR;
//        }
//
//        return str.substring(start);
//    }
//
//    /**
//     * 截取字符串
//     *
//     * @param str   字符串
//     * @param start 开始
//     * @param end   结束
//     * @return 结果
//     */
//    public static String substring(final String str, int start, int end) {
//        if (str == null) {
//            return NULLSTR;
//        }
//
//        if (end < 0) {
//            end = str.length() + end;
//        }
//        if (start < 0) {
//            start = str.length() + start;
//        }
//
//        if (end > str.length()) {
//            end = str.length();
//        }
//
//        if (start > end) {
//            return NULLSTR;
//        }
//
//        if (start < 0) {
//            start = 0;
//        }
//        if (end < 0) {
//            end = 0;
//        }
//
//        return str.substring(start, end);
//    }
//
//    /**
//     * 判断是否为空，并且不是空白字符
//     *
//     * @param str 要判断的value
//     * @return 结果
//     */
//    public static boolean hasText(String str) {
//        return (str != null && !str.isEmpty() && containsText(str));
//    }
//
//    private static boolean containsText(CharSequence str) {
//        int strLen = str.length();
//        for (int i = 0; i < strLen; i++) {
//            if (!Character.isWhitespace(str.charAt(i))) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 格式化文本, {} 表示占位符<br>
//     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
//     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
//     * 例：<br>
//     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
//     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
//     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
//     *
//     * @param template 文本模板，被替换的部分用 {} 表示
//     * @param params   参数值
//     * @return 格式化后的文本
//     */
//    public static String format(String template, Object... params) {
//        if (isEmpty(params) || isEmpty(template)) {
//            return template;
//        }
//        return StrFormatter.format(template, params);
//    }
//
//    /**
//     * 是否为http(s)://开头
//     *
//     * @param link 链接
//     * @return 结果
//     */
//    public static boolean ishttp(String link) {
//        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
//    }
//
//    /**
//     * 驼峰转下划线命名
//     */
//    public static String toUnderScoreCase(String str) {
//        if (str == null) {
//            return null;
//        }
//        StringBuilder sb = new StringBuilder();
//        // 前置字符是否大写
//        boolean preCharIsUpperCase = true;
//        // 当前字符是否大写
//        boolean curreCharIsUpperCase = true;
//        // 下一字符是否大写
//        boolean nexteCharIsUpperCase = true;
//        for (int i = 0; i < str.length(); i++) {
//            char c = str.charAt(i);
//            if (i > 0) {
//                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
//            } else {
//                preCharIsUpperCase = false;
//            }
//
//            curreCharIsUpperCase = Character.isUpperCase(c);
//
//            if (i < (str.length() - 1)) {
//                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
//            }
//
//            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
//                sb.append(SEPARATOR);
//            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
//                sb.append(SEPARATOR);
//            }
//            sb.append(Character.toLowerCase(c));
//        }
//
//        return sb.toString();
//    }
//
//    /**
//     * 是否包含字符串
//     *
//     * @param str  验证字符串
//     * @param strs 字符串组
//     * @return 包含返回true
//     */
//    public static boolean inStringIgnoreCase(String str, String... strs) {
//        if (str != null && strs != null) {
//            for (String s : strs) {
//                if (str.equalsIgnoreCase(trim(s))) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
//     *
//     * @param name 转换前的下划线大写方式命名的字符串
//     * @return 转换后的驼峰式命名的字符串
//     */
//    public static String convertToCamelCase(String name) {
//        StringBuilder result = new StringBuilder();
//        // 快速检查
//        if (name == null || name.isEmpty()) {
//            // 没必要转换
//            return "";
//        } else if (!name.contains("_")) {
//            // 不含下划线，仅将首字母大写
//            return name.substring(0, 1).toUpperCase() + name.substring(1);
//        }
//        // 用下划线将原始字符串分割
//        String[] camels = name.split("_");
//        for (String camel : camels) {
//            // 跳过原始字符串中开头、结尾的下换线或双重下划线
//            if (camel.isEmpty()) {
//                continue;
//            }
//            // 首字母大写
//            result.append(camel.substring(0, 1).toUpperCase());
//            result.append(camel.substring(1).toLowerCase());
//        }
//        return result.toString();
//    }
//
//    /**
//     * 驼峰式命名法 例如：user_name->userName
//     */
//    public static String toCamelCase(String s) {
//        if (s == null) {
//            return null;
//        }
//        s = s.toLowerCase();
//        StringBuilder sb = new StringBuilder(s.length());
//        boolean upperCase = false;
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//
//            if (c == SEPARATOR) {
//                upperCase = true;
//            } else if (upperCase) {
//                sb.append(Character.toUpperCase(c));
//                upperCase = false;
//            } else {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
//     *
//     * @param str  指定字符串
//     * @param strs 需要检查的字符串数组
//     * @return 是否匹配
//     */
//    public static boolean matches(String str, List<String> strs) {
//        if (isEmpty(str) || isEmpty(strs)) {
//            return false;
//        }
//        for (String pattern : strs) {
//            if (isMatch(pattern, str)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断url是否与规则配置:
//     * ? 表示单个字符;
//     * * 表示一层路径内的任意字符串，不可跨层级;
//     * ** 表示任意层路径;
//     *
//     * @param pattern 匹配规则
//     * @param url     需要匹配的url
//     * @return
//     */
//    public static boolean isMatch(String pattern, String url) {
//        AntPathMatcher matcher = new AntPathMatcher();
//        return matcher.match(pattern, url);
//    }
//
//    @SuppressWarnings("unchecked")
//    public static <T> T cast(Object obj) {
//        return (T) obj;
//    }
//
//    /**
//     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
//     *
//     * @param num  数字对象
//     * @param size 字符串指定长度
//     * @return 返回数字的字符串格式，该字符串为指定长度。
//     */
//    public static final String padl(final Number num, final int size) {
//        return padl(num.toString(), size, '0');
//    }
//
//    /**
//     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
//     *
//     * @param s    原始字符串
//     * @param size 字符串指定长度
//     * @param c    用于补齐的字符
//     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
//     */
//    public static final String padl(final String s, final int size, final char c) {
//        final StringBuilder sb = new StringBuilder(size);
//        if (s != null) {
//            final int len = s.length();
//            if (s.length() <= size) {
//                for (int i = size - len; i > 0; i--) {
//                    sb.append(c);
//                }
//                sb.append(s);
//            } else {
//                return s.substring(len - size, len);
//            }
//        } else {
//            for (int i = size; i > 0; i--) {
//                sb.append(c);
//            }
//        }
//        return sb.toString();
//    }
//}

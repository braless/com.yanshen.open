package util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import com.google.common.collect.Lists;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 14:33
 **/
public class CommonUtil {

    private CommonUtil() {}

    /* **************************************/
    /* *********** 常用 **********/
    SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 生成 UUID
     * @return
     */
    public static final String createUUID (){
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    /**
     * MD5 加密
     * @param value
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static final String md5(String value) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(value.getBytes());
        return new BigInteger(1, md.digest()).toString(16);
    }

    /**
     * collection 空判断
     * @param collection
     * @return
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    /**
     * collection 非空
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * map 空判断
     * @param map
     * @return
     */
    public static boolean isEmpty(Map<?,?> map){
        return map == null || map.size() == 0;
    }
    public static boolean isNotEmpty(Map<?,?> map){
        return !isEmpty(map);
    }

    /**
     * 字符空判断
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return org.apache.commons.lang3.StringUtils.isEmpty(cs);
    }

    /**
     * 字符非空
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 对象是否空
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj){
        return Objects.isNull(obj);
    }
    public static boolean isNotEmpty(Object obj){
        return !isEmpty(obj);
    }

    public static boolean isEmpty(Object[] obj) {
        return obj==null || obj.length==0;
    }

    public static boolean isNotEmpty(Object[] obj) {
        return !isEmpty(obj);
    }

    /**
     * 实体类属性复制
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target){
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

    /**
     * 是否相等
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b){
        return Objects.equals(a, b);
    }

    /**
     * 时间戳转 LocalDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime dateTime2Timestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * LocalDateTime 转时间戳
     * @param localDateTime
     * @return
     */
    public static long timestamp2DateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    /**
     * LocalDateTime 格式化, 默认格式 yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime dateTime){
        String defaultPattern = "yyyy-MM-dd HH:mm:ss";
        return formatLocalDateTime(dateTime, defaultPattern);
    }

    /**
     * LocalDateTime 格式化
     * @param dateTime
     * @param pattern
     * @return
     */
    public static String formatLocalDateTime(LocalDateTime dateTime, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    public static String formatLocalDate(LocalDate localDate){
        String defaultPattern = "yyyy-MM-dd";
        return formatLocalDate(localDate, defaultPattern);
    }

    public static String formatLocalDate(LocalDate localDate, String pattern){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    /**
     * 随机字符串生成
     * @param length
     * @return
     */
    public static String customRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 随机字符串: apache commons 工具
     * @param length
     * @return
     */
    public static String randomString(int length){
        return org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * 随机字母或数字
     * type: 1-字母, 2-数字, 其他-字母数字混合
     * @param length
     * @param type
     * @return
     */
    public static String randomStringNumber(int length, int type) {
        StringBuilder val = new StringBuilder();
        for(int i = 0; i < length; i++) {
            if(type == 1) { // 字母
                int temp = ThreadLocalRandom.current().nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (ThreadLocalRandom.current().nextInt(26) + temp));
            } else if(type == 2) {
                val.append(String.valueOf(ThreadLocalRandom.current().nextInt(10)));
            }else {
                val = new StringBuilder(randomString(length));
            }
        }
        return val.toString();
    }

    /**
     * 转化成小写
     * @param str
     * @return
     */
    public static String lowerCase(String str){
        return org.apache.commons.lang.StringUtils.lowerCase(str);
    }
    public static String lowerCase(String str, Locale locale){
        return org.apache.commons.lang.StringUtils.lowerCase(str, locale);
    }

    /**
     * 转化成大写
     * @param str
     * @return
     */
    public static String upperCase(String str){
        return org.apache.commons.lang.StringUtils.upperCase(str);
    }
    public static String upperCase(String str, Locale locale){
        return org.apache.commons.lang.StringUtils.upperCase(str, locale);
    }

    /**
     * 是否数值
     * @param str
     * @return
     */
    public static boolean isNumericVal(String str){
        if(str==null||str.length()<=0){
            return false;
        }
        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        return str.matches(regex);
    }
    /**
     * 是否数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        if(str==null||str.length()<=0)
            return false;
        String regex = "^[0-9]*$";
        return str.matches(regex);
    }

    /**
     * fastJson 对象转 json
     * @param obj
     * @return
     * @throws Exception
     */
    public static String obj2json(Object obj) {
        return JSON.toJSONString(obj);
    }

    /**
     * fastJson json 转对象
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T json2obj(String jsonStr, Class<T> clazz) {
        return JSON.parseObject(jsonStr, clazz);
    }

    /**
     * fastJson json 转 map
     * @param jsonStr
     * @return
     * @throws Exception
     */
    public static Map<String, Object> json2map(String jsonStr) {
        return JSON.parseObject(jsonStr, Map.class);
    }

    /**
     * fastJson map 转对象
     * @param map
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T map2obj(Map<?, ?> map, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    public static void sendSingleShortMsg(String phone, String message){
        SmsEntity smsEntity = new SmsEntity(phone, message);
        //SmsClientUtil.sendPush(smsEntity);
    }

    /* **************************************/
    /* *********** 非常用 **********/
    /**
     * 中文标点符号转英文字标点符号
     * @param str  原字符串
     * @return str 新字符串
     */
    public static final String symbolCN2EN(String str) {
        String[] regs = { "！", "，", "。", "；", "~", "《", "》", "（", "）", "？",
                "”", "｛", "｝", "“", "：", "【", "】", "”", "‘", "’", "!", ",",
                ".", ";", "`", "<", ">", "\\(", "\\)", "\\?", "'", "\\{", "}", "\"",
                ":", "\\{", "}", "\"", "\'", "\'" };
        for (int i = 0; i < regs.length / 2; i++) {
            str = str.replaceAll(regs[i], regs[i + regs.length / 2]);
        }
        return str;
    }

    /**
     * 发放机接入 MD5 加密方式
     * 说明: 在某些数据加密上和上面的 md5 加密结果会有点不同, 如: 1544836416781LYZH2015ca3ffa0648b4427998975cd5363e5eb5
     * @param origin
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public static String MD5Encode(String origin) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return MD5Encode(origin, "UTF-8");
    }

    public static String MD5Encode(String origin, String charsetName) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String resultString = new String(origin);
        MessageDigest md = MessageDigest.getInstance("MD5");
        if (isEmpty(charsetName)){
            resultString = byteToHexString(md.digest(resultString.getBytes()));
        }
        else{
            resultString = byteToHexString(md.digest(resultString.getBytes(charsetName)));
        }

        return resultString;
    }

    /**
     * 将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * 转 16 进制, 同 parseByte2HexStr
     * @param b
     * @return
     */
    public static String byteToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n & (16 - 1);
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1) return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    /**
     * 根据分隔符转成驼峰形式
     * @param str
     * @return
     */
    public static String lower2UpperHump(String str, String split) {
        if(isEmpty(str) || isEmpty(split)){
            return null;
        }
        String[] strs = str.split(split);
        StringBuilder sb = new StringBuilder();
        Arrays.stream(strs).forEach(s ->{
            char[] ch = s.toCharArray();
            if(ch[0] >= 'a' && ch[0] <= 'z'){
                ch[0] = (char)(ch[0] - 32);
            }
            sb.append(ch);
        });

        return sb.toString();
    }

    /**
     * 获取和当前时间的时间差的 时间
     *
     * @param time
     * 		时间
     * @return
     */
    public static Date diffTime(int time) throws Exception{
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, time);// 时间分钟之前的时间
        Date beforeD = beforeTime.getTime();
        return beforeD;
    }

    /**
     * 得到 汉字的全拼
     * @param src 中文字符串
     * @return
     */
    public static String getPingYin(String src) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder sb = new StringBuilder();
        char[] srcArray = src.toCharArray();
        try {
            for (int i = 0; i < srcArray.length; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(srcArray[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] targetArray = PinyinHelper.toHanyuPinyinStringArray(srcArray[i], format);
                    sb.append(targetArray[0]);
                } else {
                    sb.append(java.lang.Character.toString(srcArray[i]));
                }
            }
            return sb.toString();
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return sb.toString();
    }
    /**
     * 得到中文首字母,例如"专科"得到zk返回
     * @param str 中文字符串
     * @return
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char word = str.charAt(i);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                sb.append(pinyinArray[0].charAt(0));
            } else {
                sb.append(word);
            }
        }
        return sb.toString();
    }
    /**
     * 将字符串转译为ASCII码
     * @param cnStr  中文字符串
     * @return
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder sb = new StringBuilder();
        byte[] bGBK = cnStr.getBytes();
        for (int i = 0; i < bGBK.length; i++) {
            sb.append(Integer.toHexString(bGBK[i] & 0xff));
        }
        return sb.toString();
    }

    /**
     * 手机号码5-9位替换*号
     * @param phoneNum
     * @return
     */
    public static String changePhoneNum(String phoneNum) {
        StringBuilder result = null;
        if(phoneNum.length()<3) {
            result = new StringBuilder(phoneNum);
        }else if(phoneNum.length() <= 6){
            result = new StringBuilder(phoneNum.substring(0, 3));
            for (int i = 0; i < phoneNum.length(); i++) {
                if(i>2) {
                    result.append("*");
                }
            }
        }else {
            result = new StringBuilder(phoneNum.substring(0, 3) + "****" + phoneNum.substring(7, phoneNum.length()));
        }
        return result.toString();
    }

    public static String hideSectionPhone(String phone){
        return hideSectionWords(phone, 4, 4, "*", "asc");
    }

    /**
     * 隐藏部分字符
     * @param words 原始字符
     * @param hideNum 隐藏个数
     * @param startChar 起始隐藏, 从 1 开始
     * @return
     */
    public static String hideSectionWords(String words, int hideNum, int startChar){
        return hideSectionWords(words, hideNum, startChar, "asc");
    }

    public static String hideSectionWords(String words, int hideNum, int startChar, String order){
        return hideSectionWords(words, hideNum, startChar, "*", order);
    }

    /**
     * 隐藏部分字符
     * @param words 原始字符
     * @param hideNum 隐藏个数
     * @param startChar 起始隐藏, 从 1 开始
     * @param hideSign 隐藏字符
     * @param order 隐藏方式 asc-正序 desc-逆序 默认 asc
     * @return
     */
    public static String hideSectionWords(String words, int hideNum, int startChar, String hideSign, String order){
        List orderList = Lists.newArrayList("asc","desc");
        order = isNotEmpty(order) && orderList.contains(order)? order : "asc";
        if(equals(order, "asc")){
            return hideWordAsc(words, hideNum, startChar, hideSign);
        }
        if(equals(order, "desc")){
            return hideWordDesc(words, hideNum, startChar, hideSign);
        }
        return null;
    }

    private static String hideWordAsc(String words, int hideNum, int startChar, String hideSign){
        int wordsLength = words.length();
        StringBuilder sb = new StringBuilder(wordsLength);
        startChar--;
        if(startChar >= wordsLength){
            return words;
        }else if((startChar + hideNum) > wordsLength ){
            sb.append(words, 0, startChar);
            for(int i=startChar; i<wordsLength; i++){
                sb.append(hideSign);
            }
            return sb.toString();
        }else{
            sb.append(words, 0, startChar);
            for(int i=startChar, len=(hideNum + startChar); i<len; i++){
                sb.append(hideSign);
            }
            sb.append(words, (hideNum + startChar), wordsLength);
            return sb.toString();
        }
    }

    private static String hideWordDesc(String words, int hideNum, int startChar, String hideSign){
        int wordsLength = words.length();
        StringBuilder sb = new StringBuilder(wordsLength);
        if(startChar >= wordsLength){
            return words;
        }else if((startChar + hideNum) > wordsLength ){
            for(int i=startChar; i>=0; i--){
                sb.append(hideSign);
            }
            sb.append(words, startChar, wordsLength);
            return sb.toString();
        }else{
            sb.append(words, 0, wordsLength-(hideNum + startChar));
            for(int i=(wordsLength-startChar), len=wordsLength-(hideNum + startChar); i>=len; i--){
                sb.append(hideSign);
            }
            sb.append(words, (wordsLength-startChar), wordsLength);
            return sb.toString();
        }
    }
    /**
     * 替换模板变量
     * @param templateContent
     * @param map key代表模板中的变量名，value就是要最终显示的值
     * @return
     */
    public static String replaceTemplate(String templateContent,Map<String,Object> map){
        if(StringUtils.isNotBlank(templateContent)) {
            Set<String> keys = map.keySet();
            for (Iterator<String> it = keys.iterator(); it.hasNext(); ) {
                String key = it.next();
                templateContent = templateContent.replace("#{" + key + "}#", (String) map.get(key));
            }
        }
        return templateContent;
    }
    /**
     *	1:上个月月份  2:上个月日期  3:上个月第一天开始时间
     * @return
     */
    public static String getLastMonthFirstDay(Integer type) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        if(type == 1){
            SimpleDateFormat sdfMonth = new SimpleDateFormat("yyyy-MM");
            return sdfMonth.format(calendar.getTime());
        }else if(type == 2){
            SimpleDateFormat sdfDay = new SimpleDateFormat("yyyy-MM-dd");
            return sdfDay.format(calendar.getTime());
        }else if(type == 3){
            SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String first = sdfTime.format(calendar.getTime())+" 00:00:00";
            return first;
        }else{
            return null;
        }
    }

    /**
     * 上个月最后一天的日期
     * @return
     */
    public static Date getLastMonthFinalDay() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        SimpleDateFormat sdfA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String last = sdfA.format(calendar.getTime())+" 23:59:59";
        return sdfB.parse(last);
    }

    /**
     * 获取某年第一天日期
     * @param year
     * @return
     */
    public static Date getCurrYearFirst(int year) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        SimpleDateFormat sdfA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currYearFirst = calendar.getTime();
        String first = sdfA.format(currYearFirst)+" 00:00:00";
        return sdfB.parse(first);
    }

    /**
     * 获取某年最后一天日期
     * @param year 年份
     * @return Date
     */
    public static Date getCurrYearLast(int year) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        SimpleDateFormat sdfA = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currYearLast = calendar.getTime();
        String last = sdfA.format(currYearLast)+" 23:59:59";
        return sdfB.parse(last);
    }

    /**
     * 通過value 取key
     *
     * @param map
     * @param value
     * @return
     */
    public static Integer getKey(HashMap<Integer,String> map,String value){
        Integer key = null;
        for(Integer getKey: map.keySet()){
            if(map.get(getKey).equals(value)){
                key = getKey;
            }
        }
        return key;
    }

    /***
     * 除法计算  如果被除数是0，则默认改为1
     * @param b1 除数
     * @param b2 被除数
     * @param scale
     * @param roundingMode
     * @return
     */
    public static BigDecimal divideDefaultOne(BigDecimal b1, BigDecimal b2, int scale, RoundingMode roundingMode){
        if(b2.compareTo(BigDecimal.ZERO) == 0){
            b2 = BigDecimal.valueOf(1L);
        }
        return b1.divide(b2,scale,roundingMode);
    }
    /**
     * ip地址格式化/app版本号格式化
     * 4.0.2.0 --->004.000.002.000
     * 4.0.7 --> 004.000.007.000
     * @param ip
     * @return
     */
    public static String ipFormat(String ip){
        String[] ips = ip.split("\\.");
        int ip1=0;
        int ip2=0;
        int ip3=0;
        int ip4=0;
        for(int i=0;i<ips.length;i++){
            if(i==0){
                ip1 = Integer.valueOf(ips[0]);
            }else if(i==1){
                ip2 = Integer.valueOf(ips[1]);
            }else if(i==2){
                ip3 = Integer.valueOf(ips[2]);
            }else if(i==3){
                ip4 = Integer.valueOf(ips[3]);
            }
        }
        return String.format("%1$03d.%2$03d.%3$03d.%4$03d", ip1,ip2,ip3,ip4);
    }


    public static String getIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (Objects.equals("127.0.0.1", ipAddress) || Objects.equals("0:0:0:0:0:0:0:1", ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                    ipAddress = inet.getHostAddress();
                } catch (UnknownHostException e) {
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) { //"***.***.***.***".length() = 15
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }


    public static void main(String[] args) throws ParseException {
//		String aa = "123";
//		System.out.println(hideSectionWords(aa, aa.length()-4, 4,"ddd"));
//		String temp="验证码：#{verificationCode}#，用于运维app登录，10分钟内有效，请勿泄漏。";
//		Map<String,Object> map=new HashMap<>();
//		map.put("verificationCode","1236");
//		System.out.println(replaceTemplate(temp,map));
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date= getLastMonthFirstDay();
//		Date date1= getLastMonthFinalDay();
//		System.out.println(sdf.format(date));
//		System.out.println(sdf.format(date1));
//		Date date = getCurrYearFirst(2018);
//		Date date1 = getCurrYearLast(2018);
//		System.out.println(sdf.format(date));
//    	System.out.println(sdf.format(date1));
    }
}

package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @Auther: wangminmeng
 * @Date: 2020/7/8 14:54
 * @Description: 排序工具类
 */
public class ListSortUtil {
    private static Logger logger = LoggerFactory.getLogger(ListSortUtil.class);

    /**
     *  *[简述]: List 泛型 排序
     *  * @param list 源数据 排序集合
     *  * @param orderField 排序的数据字段名称
     *  * @param sort 升序 还是 降序，默认升序
     *  * @param <T> 泛型T
     *  * @return List
     *  
     */
    public static <T> List<T> sort(List<T> list, final String orderField, final boolean isAsc) {
        list.sort((o1, o2) -> {
            int ret = 0;
            try {
                Method method1 = o1.getClass().getMethod(getMethodName(orderField));
                Method method2 = o2.getClass().getMethod(getMethodName(orderField));
                Field field1 = o1.getClass().getDeclaredField(orderField);
                field1.setAccessible(true);
                Class<?> type = field1.getType();
                if (type == int.class) {
                    ret = Integer.compare(field1.getInt(o1), field1.getInt(o2));
                } else if (type == double.class) {
                    ret = Double.compare(field1.getDouble(o1), field1.getDouble(o2));
                } else if (type == long.class) {
                    ret = Long.compare(field1.getLong(o1), field1.getLong(o2));
                } else if (type == float.class) {
                    ret = Float.compare(field1.getFloat(o1), field1.getFloat(o2));
                } else if (type == Date.class) {
                    long s1 = 0L;
                    long s2 = 0L;
                    Object t1 = field1.get(o1);
                    if (t1 != null) {
                        s1 = ((Date) t1).getTime();
                    }
                    Object t2 = field1.get(o2);
                    if (t2 != null) {
                        s2 = ((Date) t2).getTime();
                    }
                    ret = Long.compare(s1, s2);
                } else if (type == BigDecimal.class) {
                    BigDecimal s1 = BigDecimal.ZERO;
                    BigDecimal s2 = BigDecimal.ZERO;
                    Object t1 = field1.get(o1);
                    if (t1 != null) {
                        s1 = (BigDecimal) t1;
                    }
                    Object t2 = field1.get(o2);
                    if (t2 != null) {
                        s2 = (BigDecimal) t2;
                    }
                    ret = s1.compareTo(s2);
                } else if (isNumStr(String.valueOf(field1.get(o1))) && isNumStr(String.valueOf(field1.get(o2)))) {
                    ret = (obj2Double(method1.invoke(o1))).compareTo(obj2Double(method2.invoke(o2)));
                } else {
                    String s1 = "";
                    String s2 = "";
                    Object t1 = field1.get(o1);
                    if (t1 != null) {
                        s1 = String.valueOf(t1);
                    }
                    Object t2 = field1.get(o2);
                    if (t2 != null) {
                        s2 = String.valueOf(t2);
                    }
                    ret = s1.compareTo(s2);
                }
            } catch (Exception e) {
                logger.error("", e);
            }
            if (isAsc) {
                return ret;
            } else {
                return -1 * ret;
            }
        });
        return list;
    }


    private static Double obj2Double(Object obj) {
        String str = String.valueOf(obj);
        if (str.endsWith("%")) {
            str = str.substring(0, str.length() - 1);
        }
        return new Double(str);
    }

    private static boolean isNumStr(String str) {
        boolean flag = false;
        if (str.endsWith("%")) {
            str = str.substring(0, str.length() - 1);
        }
        if (isInteger(str) || isDecimal(str)) {
            flag = true;
        }
        return flag;
    }


    private static boolean isInteger(String str) {
        Matcher matcher = Pattern.compile("^[+-]?[0-9]+$").matcher(str);
        return matcher.find();
    }


    private static boolean isDecimal(String str) {
        return str.matches("[\\d]+\\.[\\d]+");
    }


    private static String getMethodName(String str) {
        return "get" + firstLetterToCapture(str);
    }


    private static String firstLetterToCapture(String name) {
        char[] arr = name.toCharArray();
        arr[0] -= 32;
        return String.valueOf(arr);
    }

}
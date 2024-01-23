package com.yanshen.common.redis.component;



import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.yanshen.common.core.util.Base64StrHook;
import com.yanshen.common.core.util.IpUtils;
import com.yanshen.common.core.util.ServletUtils;
import com.yanshen.common.core.util.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.yanshen.common.redis.service.RedisService;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class NoRepeatSubmitAspect {

    private static final Logger logger = LoggerFactory.getLogger(NoRepeatSubmitAspect.class);

    private static final String SUFFIX = "SUFFIX";

    @Autowired
    private RedisService redisService;

    @Value("${redis.repeat.lock}")
    private String NO_REPEAT_LOCK_PREFIX;

    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            // 设置些属性是可以访问的
            f.setAccessible(true);
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                // 设置键值
                map.put(f.getName(), Base64StrHook.hookStr(val.toString()));
            } catch (IllegalArgumentException e) {
                logger.error("getKeyAndValue IllegalArgumentException", e);
            } catch (IllegalAccessException e) {
                logger.error("getKeyAndValue IllegalAccessException", e);
            }

        }
        String paramMap = JSONObject.toJSONString(map);
//        logger.info("扫描结果：" + paramMap);
        return map;
    }

    /**
     * 横切点
     */
    @Pointcut("@annotation(noRepeatSubmit)")
    public void repeatPoint(NoRepeatSubmit noRepeatSubmit) {
    }

    /**
     * 接收请求，并记录数据
     */
    @Around(value = "repeatPoint(noRepeatSubmit)")
    public Object doBefore(ProceedingJoinPoint joinPoint, NoRepeatSubmit noRepeatSubmit) {
        String ipAddr = IpUtils.getIpAddr(ServletUtils.getRequest());
        String key = NO_REPEAT_LOCK_PREFIX + ":" + noRepeatSubmit.location() + ":" + ipAddr;
        Object[] args = joinPoint.getArgs();
        int maxSeconds = noRepeatSubmit.maxSeconds();
        //获取当前签名值
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        if (methodSignature.getMethod().isAnnotationPresent(NoRepeatSubmit.class)) {
            //从签名中获取当前对象信息
            Method method = ((MethodSignature) signature).getMethod();
            if (method.getDeclaringClass().isInterface()) {
                try {
                    method = joinPoint.getTarget().getClass().getDeclaredMethod(joinPoint.getSignature().getName(),
                            method.getParameterTypes());
                } catch (SecurityException | NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
            String parameters = JSON.toJSONString(args[0]);
            //判断当前是否参数是否为jsonobject
            if (JSON.isValidObject(parameters)) {
                JSONObject jsonObjectParam = JSON.parseObject(parameters);
                //判断当前接口是否存在签名值
                if (jsonObjectParam.containsKey("sign")) {
                    //以签名值为重复校验依据
                    parameters = jsonObjectParam.getString("sign");
                } else if (jsonObjectParam.containsKey("jilin")) {
                    //以签名值为重复校验依据
                    parameters = StringUtils.substring(jsonObjectParam.getString("jilin"), 0, 2000);
                } else {
                    //当前参数不存在param，则进行base64字符替换
                    parameters = Base64StrHook.hookStr(parameters);
                }
                /*//当前属于jsonObject-判断当前json中是否存在param
                JSONObject jsonObjectParam = JSON.parseObject(parameters);
                //当前存在param属性
                if (jsonObjectParam.containsKey("param")) {
                    //判断param属性是否存在值，存在则进行长度截取
                    if (StringUtils.isNotEmpty(jsonObjectParam.getString("param")) && jsonObjectParam.getString("param").length() > 500) {
                        //param重新截取然后赋值
                        jsonObjectParam.put("param", StringUtils.substring(jsonObjectParam.getString("param"), jsonObjectParam.getString("param").length() - 500, jsonObjectParam.getString("param").length()));
                    }
                    parameters = jsonObjectParam.toString();
                } else {
                    //当前参数不存在param，则进行base64字符替换
                    parameters = Base64StrHook.hookStr(parameters);
                }*/
            } else {
                //非jsonObject则，判断长度是否超过2000，超过则截取
                parameters = StringUtils.substring(parameters, 0, 2000);
            }
            if (StringUtils.isNotEmpty(parameters)) {
                String redisKey = key + ":" + parameters;
                int seconds = noRepeatSubmit.seconds();
                seconds = seconds > maxSeconds ? maxSeconds : seconds;
                if (redisService.hasKey(redisKey)) {
//                    //if (noRepeatSubmit.location().contains("/guarantee") || noRepeatSubmit.location().contains("/jyzx")) {
//                    if (method.getReturnType() == ChannelR.class) {
//                        return ChannelR.fail(ChannelResultEnum.FILE_500.getCode(), noRepeatSubmit.tip());
//                    } else {
//                        return R.fail(noRepeatSubmit.tip());
//                    }
                }
                redisService.setCacheObject(redisKey, "NoRepeatSubmit", Integer.toUnsignedLong(seconds));
                try {
                    Object proceed = joinPoint.proceed();
                    return proceed;
                } catch (Throwable throwable) {
                    //if (noRepeatSubmit.location().contains("/guarantee") || noRepeatSubmit.location().contains("/jyzx")) {
//                    if (method.getReturnType() == ChannelR.class) {
//                        return ChannelR.fail(ChannelResultEnum.FILE_500.getCode(), noRepeatSubmit.tip());
//                    } else {
//                        return R.fail(throwable.getMessage());
//                    }
                }
            }
        }
        return null;
    }


}

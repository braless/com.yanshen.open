package com.yanshen.common.core.util.msg;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson2.JSONObject;

import com.yanshen.common.core.util.DateUtils;
import com.yanshen.common.core.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: baohao-jia
 * @date: 2023年08月04日 15:02
 * @ClassName: webhookUtils
 * @Description: 企业微信机器人消息通知
 */
@Slf4j
public class WebhookUtils {

    /**
     * @Description: 以markdown格式发送机器人消息
     * @Param: [urlStr, msg]
     * @return: void
     * @Author: baohao-jia
     * @Date: 15:04 2023-08-04
     */
    public static void markdownMsg(String urlStr, StringBuffer msg) {
        //判定机器人地址是否为空，不为空则通知
        if (StringUtils.isNotEmpty(urlStr)) {
            //异常消息预警-如保函推送失败则进行信息收集用于预警
            JSONObject jsonObjectError = new JSONObject();
           /* jsonObjectError.put("msgtype", "text");
            JSONObject jsonObjectErrorMsg = new JSONObject();
            jsonObjectErrorMsg.put("content", msg);
            List mentionedList = new ArrayList<>();
            *//**无法获取企业微信用户id，因此通过手机号进行人员执行*//*
            //mentionedList.add("@all");
            //mentionedList.add(userId);
            jsonObjectErrorMsg.put("mentioned_list", mentionedList);
            List mentionedMobileList = new ArrayList<>();
            //mentionedMobileList.add("@all");
            // mentionedMobileList.add("18070282946");
            mentionedMobileList.add("@all");
            jsonObjectErrorMsg.put("mentioned_mobile_list", mentionedMobileList);
            jsonObjectError.put("text", jsonObjectErrorMsg);
            String result = HttpRequest.post(urlStr).timeout(60000)
                    .keepAlive(true)
                    .contentType("application/json;charset=UTF-8")
                    .body(jsonObjectError.toJSONString()).execute().body();*/
            jsonObjectError.put("msgtype", "markdown");
            JSONObject jsonObjectErrorMsg = new JSONObject();
            jsonObjectErrorMsg.put("content", msg);
            jsonObjectError.put("markdown", jsonObjectErrorMsg);
            String result = HttpRequest.post(urlStr).timeout(60000)
                    .keepAlive(true)
                    .contentType("application/json;charset=UTF-8")
                    .body(jsonObjectError.toJSONString()).execute().body();
            log.info("时间：{},消息发送结果：{}", DateUtils.getTime(), result);
        }
    }

    /**
     * @Description: 以text文本格式发送机器人消息
     * @Param: [urlStr, msg, userId, phoneModel]
     * @return: void
     * @Author: baohao-jia
     * @Date: 15:28 2023-08-04
     */
    public static void textMsg(String urlStr, String msg, String userId, String phoneModel) {
        //判定机器人地址是否为空，不为空则通知
        if (StringUtils.isNotEmpty(urlStr)) {
            JSONObject jsonObjectError = new JSONObject();
            jsonObjectError.put("msgtype", "text");
            JSONObject jsonObjectErrorMsg = new JSONObject();
            jsonObjectErrorMsg.put("content", msg);
            List mentionedList = new ArrayList<>();
            /**无法获取企业微信用户id，因此通过手机号进行人员执行*/
            //mentionedList.add("@all");
            //mentionedList.add(userId);
            jsonObjectErrorMsg.put("mentioned_list", mentionedList);
            List mentionedMobileList = new ArrayList<>();
            //mentionedMobileList.add("@all");
            // mentionedMobileList.add("18070282946");
            /**未传入指定人员手机号，则默认通知所有人*/
            if (StringUtils.isNotEmpty(phoneModel)) {
                String[] str = phoneModel.split(",");
                for (String s : str) {
                    mentionedMobileList.add(s);
                }
            } else {
                mentionedMobileList.add("@all");
            }
            jsonObjectErrorMsg.put("mentioned_mobile_list", mentionedMobileList);
            jsonObjectError.put("text", jsonObjectErrorMsg);
//            String result = HttpRequest.post(urlStr).timeout(60000)
//                    .keepAlive(true)
//                    .contentType("application/json;charset=UTF-8")
//                    .body(jsonObjectError.toJSONString()).execute().body();
            log.info("时间：{},消息发送结果：{}", DateUtils.getTime(), "result");
        }
    }

    public static void main(String[] args) {
        StringBuffer content = new StringBuffer();
        content.append("广州今日天气：\r\n 29度，大部分多云，降雨概率：60%");
        textMsg("https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=404c93ab-f0af-425b-8283-79198954320a", content.toString(), null, null);
    }
}

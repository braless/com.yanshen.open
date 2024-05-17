package com.yanshen.messager.config;

import com.alibaba.fastjson2.JSON;
import com.yanshen.messager.domain.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * @author
 * @version 1.0
 * @description: TODO
 * @date 2023/4/4 16:42
 */
@Slf4j
public class MessageEditEncoder implements Encoder.Text<Message> {

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public String encode(Message message) throws EncodeException {
        try {
            return JSON.toJSONString(message);
        } catch (Exception e) {
            e.printStackTrace();
//            log.info("服务端数据转换json结构失败！");
            return "";
        }
    }
}

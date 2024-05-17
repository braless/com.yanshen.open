package com.yanshen.messager.config;

import com.alibaba.fastjson2.JSONObject;
import com.yanshen.messager.domain.Message;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * @author
 * @version 1.0
 * @description: TODO
 * @date 2023/4/4 16:46
 */
@Slf4j
public class MessageEditDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String jsonMessage) throws DecodeException {
        return JSONObject.parseObject(jsonMessage, Message.class);
    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            JSONObject.parseObject(jsonMessage, Message.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
//            log.info("客户端发送消息到服务端，数据解析失败！");
            return false;
        }
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}

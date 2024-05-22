package com.yanshen.messager.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import javax.websocket.Session;
import java.io.IOException;

/**
 * 消息订阅监听类
 *
 * @author baohao-jia
 * @since 2023年11月16日 16:20
 */
public class SubscribeListener implements MessageListener {

    private Session session;

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

//    @Autowired
//    private OrderTraceRecordService orderTraceRecordService;


    /**
     * 接收发布者的消息
     *
     * @param message,pattern
     * @return void
     * @author baohao-jia
     * @date 16:21 2023-11-16
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        System.out.println(new String(pattern) + "主题发布：" + msg);
        if (null != session && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        else {
//            String channel = new String(message.getChannel());
//            switch (channel){
//                case "order_status_change":
//                    //插入订单变更记录
//                    orderTraceRecordService.insertOrderTraceRecord(msg);
//                    break;
//                case "2":
//                    //
//                    break;
//            }
//        }

    }

}

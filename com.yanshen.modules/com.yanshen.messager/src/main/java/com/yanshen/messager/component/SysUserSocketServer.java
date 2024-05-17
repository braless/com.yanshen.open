package com.yanshen.messager.component;


import com.yanshen.common.core.util.SpringUtils;
import com.yanshen.common.core.util.StringUtils;
import com.yanshen.messager.listener.SubscribeListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;


@ServerEndpoint(value = "/ws/sysUser/{userId}")
@Slf4j
@Component
public class SysUserSocketServer {


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineCount = new AtomicInteger(0);
    //concurrent包的线程安全Set，用来存放每个客户端对应的webSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<SysUserSocketServer> webSocketSet = new CopyOnWriteArraySet<SysUserSocketServer>();
    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    private SubscribeListener subscribeListener;

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(value = "userId") Long userId) {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("admin有新连接加入！当前在线人数为{}", getOnlineCount());
        subscribeListener = new SubscribeListener();
        subscribeListener.setSession(session);
        //设置订阅topic
        redisMessageListenerContainer.addMessageListener(subscribeListener, new ChannelTopic("TOPIC" + userId));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        redisMessageListenerContainer.removeMessageListener(subscribeListener);
        log.info("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        if (StringUtils.isBlank(message)) {
            log.info("SysUserSocketServer.onMessage消息为空。");
            return;
        }
        //群发消息
        for (SysUserSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public int getOnlineCount() {
        return onlineCount.get();
    }

    public void addOnlineCount() {
        SysUserSocketServer.onlineCount.getAndIncrement();
    }

    public void subOnlineCount() {
        SysUserSocketServer.onlineCount.getAndDecrement();
    }


    /*** 以下为旧版代码 ***/
    /*//与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //用来存在线连接数
    private static Map<Long, Session> sessionPool = new ConcurrentHashMap<>();

    //链接的IDs
    private static Map<String, Long> sessionIds = new ConcurrentHashMap<>();

    *//**
     * 链接成功调用的方法
     *
     * @param session
     * @param userId
     *//*
    @OnOpen
    public void open(Session session, @PathParam(value = "userId") Long userId)
    {
        this.session = session;
        sessionPool.put(userId, session);
        sessionIds.put(session.getId(), userId);
    }

    *//**
     * 收到客户端消息后调用的方法
     *
     * @param message
     *//*
    @OnMessage
    public void onMessage(String message)
    {
//        log.info("当前发送人sessionid为" + session.getId() + "发送内容为" + message);
    }

    *//**
     * 连接关闭触发
     *//*
    @OnClose
    public void onClose()
    {
        sessionPool.remove(sessionIds.get(session.getId()));
        sessionIds.remove(session.getId());
    }

    *//**
     * 发生错误时触发
     *
     * @param session
     * @param error
     *//*
    @OnError
    public void onError(Session session, Throwable error)
    {
        error.printStackTrace();
    }

    *//**
     * 信息发送的方法
     *
     * @param message
     * @param userId
     *//*
    public static void sendMessage(String message, Long userId)
    {
        Session session = sessionPool.get(userId);
        if (session != null && session.isOpen())
        {
            try
            {
                //同步发送
                session.getBasicRemote().sendText(message);
                //异步发送
//                session.getAsyncRemote().sendText(message);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    *//**
     * 获取当前连接数
     *
     * @return
     *//*
    public static int getOnlineNum()
    {
        return sessionPool.size();
    }

    *//**
     * 获取在线用户名以逗号隔开
     *
     * @return
     *//*
    public static String getOnlineUsers()
    {
        StringBuffer users = new StringBuffer();
        for (String key : sessionIds.keySet())
        {
            users.append(sessionIds.get(key) + ",");
        }
        return users.toString();
    }

    *//**
     * 信息群发
     *
     * @param msg
     *//*
    public static void sendAll(String msg)
    {
        for (String key : sessionIds.keySet())
        {
            sendMessage(msg, sessionIds.get(key));
        }
    }

    *//**
     * 多个人发送给指定的几个用户
     *
     * @param msg
     * @param persons 用户s
     *//*

    public static void sendMany(String msg, List<Long> persons)
    {
        for (Long userid : persons)
        {
            sendMessage(msg, userid);
        }
    }

    *//**
     * 校验用户ID是否连接
     * @param userId
     * @return
     *//*
    public boolean checkUserOnline(Long userId)
    {
        if (sessionIds.containsValue(userId))
        {
            return true;
        }
        else
        {
            return false;
        }

    }*/

}

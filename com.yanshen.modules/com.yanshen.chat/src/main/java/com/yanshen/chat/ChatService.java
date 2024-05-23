package com.yanshen.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: Yanchao
 * @Date: 2024/5/22 下午3:58
 * @Version: v1.0.0
 * @Description: TODO
 **/
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class ChatService {
    public static void main(String[] args) {
        SpringApplication.run(ChatService.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  聊天模块启动成功   ლ(´ڡ`ლ)ﾞ ");
    }
}

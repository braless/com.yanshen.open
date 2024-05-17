package com.yanshen.messager.domain;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 15:57
 * @Description:
 * @Location: com.yanshen.messager.domain
 * @Project: com.yanshen.open
 */
@Data
public class Message implements Serializable {

    private Long id;
    private String sender;
    private Long userId;
    private String senderName;
    public String sendUrl;

    public String content;
    public LocalDateTime sendTime;
}

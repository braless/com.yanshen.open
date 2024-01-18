package com.yanshen.messager.domain;

import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 15:57
 * @Description:
 * @Location: com.yanshen.messager.domain
 * @Project: com.yanshen.open
 */
@Data
public class Messager {

    private String senderName;

    public String sendUrl;

    public String sendTime;
}

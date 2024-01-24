package com.yanshen.messager.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 15:57
 * @Description:
 * @Location: com.yanshen.messager.domain
 * @Project: com.yanshen.open
 */
@Data
public class MessageDto {

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("发送人名称")
    private String senderName;
    @ApiModelProperty("发送人id")
    private String sendUrl;
    @ApiModelProperty("发送时间")
    private LocalDateTime sendTime;

    /**
     * 请求参数
     */
    private Map<String, Object> params = new HashMap<>();
}

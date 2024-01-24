package com.yanshen.messager.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 15:57
 * @Description:
 * @Location: com.yanshen.messager.domain
 * @Project: com.yanshen.open
 */
@Data
public class MessageVo {

    @ApiModelProperty("主键")
    private Long id;
    @ApiModelProperty("发送人名称")
    private String senderName;
    @ApiModelProperty("发送人id")
    public String sendUrl;
    @ApiModelProperty("发送时间")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    public LocalDateTime sendTime;
}

package com.yanshen.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-23 14:20
 * @Description:
 * @Location: com.yanshen.auth.domain
 * @Project: com.yanshen.open
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "UserBody", description = "登录对象")
public class UserBody {

    @ApiModelProperty(value = "客户端ID", required = true)
    private String clientId;

    @ApiModelProperty(value = "客户端密钥", required = true)
    private String clientSecret;

    @ApiModelProperty(value = "序列号-正常ca登录", required = false)
    private String dognum;

    @ApiModelProperty(value = "uuid-用于支持部分交易中心跳转时的单点登录", required = false)
    private String uuid;

    /**
     * 扫码登录时必须
     */
    @ApiModelProperty(value = "二维码id", required = false)
    private String qrCodeInfo;

    /**
     * 扫码登录时必须
     */
    @ApiModelProperty(value = "移动扫码登录平台码", required = false)
    private String platformCode;
}

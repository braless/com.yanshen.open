package com.yanshen.auth.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-23 14:13
 * @Description:
 * @Location: com.yanshen.auth.domain
 * @Project: com.yanshen.open
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "CaLoginBody", description = "CA登录对象")
public class BackendBody {
    /**
     * 客户端ID
     */
    @ApiModelProperty(value = "客户端ID", required = true)
    private String clientId;

    /**
     * 授权类型
     */
    @ApiModelProperty(value = "授权类型（仅支持 code password client三种模式）", required = true)
    private String grantType;

    /**
     * 客户端密钥
     */
    @ApiModelProperty(value = "客户端密钥", required = true)
    private String clientSecret;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名 grantType为password时必穿")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "密码 grantType为password时必穿")
    private String password;


}

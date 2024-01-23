package com.yanshen.auth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import com.yanshen.common.core.web.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 客户端信息表
 * </p>
 *
 * @author Yanshen Acmen
 * @since 2023-03-17
 */
@Data
@Accessors(chain = true)
@TableName("oauth_client_info")
@ApiModel(value = "OauthClientInfo对象", description = "客户端信息表")
public class OauthClientInfo extends BaseEntity {

    public static final String CLIENT_ID = "client_id";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String RESOURCE_IDS = "resource_ids";
    public static final String BUSINESS_TYPE = "business_type";
    public static final String CLIENT_NAME = "client_name";
    public static final String SOURCE = "source";
    public static final String SCOPE = "scope";
    public static final String AUTHORIZED_GRANT_TYPES = "authorized_grant_types";
    public static final String WEB_SERVER_REDIRECT_URI = "web_server_redirect_uri";
    public static final String ACCESS_TOKEN_VALIDITY = "access_token_validity";
    public static final String REFRESH_TOKEN_VALIDITY = "refresh_token_validity";
    public static final String AUTOAPPROVE = "autoapprove";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String ORG_ID = "org_id";
    @ApiModelProperty("客户端ID（第三方系统的唯一ID）")
    @TableId(value = "client_id", type = IdType.ASSIGN_ID)
    private String clientId;
    @ApiModelProperty("客户端密钥（第三方系统的登录密码）")
    @TableField("client_secret")
    private String clientSecret;
    @ApiModelProperty("客户端所能访问的资源id集合，多个资源时用逗号(,)分隔")
    @TableField("resource_ids")
    private String resourceIds;
    @ApiModelProperty("业务类型( `SYS_USER` 综合管理后台  `SYS_PORTAL`门户)")
    @TableField("business_type")
    private String businessType;
    @ApiModelProperty("客户端名称")
    @TableField("client_name")
    private String clientName;
    @ApiModelProperty("第三方用户来源（ZKSK、GITHUB、GITEE、QQ等）")
    @TableField("`source`")
    private String source;
    @ApiModelProperty("客户端申请的权限范围，可选值包括read,write,trust;若有多个权限范围用逗号(,)分隔")
    @TableField("scope")
    private String scope;
    @ApiModelProperty("客户端支持的授权许可类型(grant_type)，可选值包括code,password,client,若支持多个授权许可类型用逗号(,)分隔")
    @TableField("authorized_grant_types")
    private String authorizedGrantTypes;
    @ApiModelProperty("客户端重定向URI，当grant_type为code时, 在Oauth的流程中会使用并检查与数据库内的redirect_uri是否一致")
    @TableField("web_server_redirect_uri")
    private String webServerRedirectUri;
    @ApiModelProperty("设定客户端的access_token的有效时间值(单位:秒)，若不设定值则使用默认的有效时间值(60 * 60 * 12, 12小时)")
    @TableField("access_token_validity")
    private String accessTokenValidity;
    @ApiModelProperty("设定客户端的refresh_token的有效时间值(单位:秒)，若不设定值则使用默认的有效时间值(60 * 60 * 24 * 30, 30天)")
    @TableField("refresh_token_validity")
    private String refreshTokenValidity;
    @ApiModelProperty("设置用户是否自动批准授予权限操作, 默认值为 ‘false’, 可选值包括 ‘true’,‘false’, ‘read’,‘write’.")
    @TableField("autoapprove")
    private String autoapprove;
    @ApiModelProperty("对应的内部客户ID（客户第三方登录使用该字段）")
    @TableField("customer_id")
    private String customerId;
    @ApiModelProperty("对应的内部机构ID（机构第三方登录使用该字段）")
    @TableField("org_id")
    private String orgId;

    @Override
    public Serializable pkVal() {
        return this.clientId;
    }

}

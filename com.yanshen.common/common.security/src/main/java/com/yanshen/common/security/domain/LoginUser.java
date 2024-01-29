package com.yanshen.common.security.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yanshen.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-29 15:01
 * @Description:
 * @Location: com.yanshen.common.security.domain
 * @Project: com.yanshen.open
 */
@Data
@Accessors(chain = true)
public class LoginUser {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "令牌（用户唯一标识）")
    private String token;

    @ApiModelProperty(value = "用户名id（综合管理后台登录此项有值）")
    private String userid;

    @ApiModelProperty(value = "企业成员id（门户登录此项有值）")
    private String memberId;

    @ApiModelProperty(value = "联系人ID（小程序登录成功此项有值）")
    private String operatorId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "登录时间")
    private Long loginTime;

    @ApiModelProperty(value = "过期时间")
    private Long expireTime;

    @ApiModelProperty(value = "登录IP地址")
    private String ipaddr;

    @ApiModelProperty(value = "授权类型（仅支持 code password client三种模式）")
    private String grantType;

    @ApiModelProperty("业务类型( `SYS_USER` 综合管理后台  `SYS_PORTAL`门户)")
    private String businessType;

    @ApiModelProperty(value = "权限列表")
    private Set<String> permissions;

    @ApiModelProperty(value = "角色列表")
    private Set<String> roles;

//    @ApiModelProperty(value = "用户信息")
//    private SysUser sysUser;

    //@ApiModelProperty(value = "企业用户信息")
    //private Member member;

    //@ApiModelProperty(value = "联系人信息")
    //private Operator operator;

    @ApiModelProperty(value = "客户端授权资源集合")
    private String resourceIds;

    @ApiModelProperty(value = "客户端申请的权限范围，可选值包括all,read,write,trust;若有多个权限范围用逗号(,)分隔 暂未完善")
    private String scope;

    @ApiModelProperty(value = "客户ID,第三方登录为客户此项有值")
    private String customerId;

    @ApiModelProperty(value = "客户对应下属平台编码 此值与标段标该字段对应")
    private String platformCode;

    @ApiModelProperty(value = "机构ID,第三方登录为客户此项有值")
    private String orgId;
}

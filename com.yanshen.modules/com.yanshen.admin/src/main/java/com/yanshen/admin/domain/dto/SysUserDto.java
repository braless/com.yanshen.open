package com.yanshen.admin.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Dto
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Data
public class SysUserDto implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     *
     */
    @ApiModelProperty(value = "ID")
    private Long id;
    /**
     *
     */
    @ApiModelProperty(value = "用户名")
    private String userName;
    /**
     *
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 状态（0正常 1停用）
     */
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private String status;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 请求参数
     */
    @ApiModelProperty(value = "请求参数")
    private Map<String, Object> params = new HashMap<>();
}

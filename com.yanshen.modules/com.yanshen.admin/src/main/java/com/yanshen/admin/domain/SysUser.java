package com.yanshen.admin.domain;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yanshen.common.core.web.domain.BasePlusEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("sys_user")
public class SysUser extends BasePlusEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    
    private Long id;
    @TableField("user_name")
    
    private String userName;
    @TableField("`password`")
    
    private String password;


}

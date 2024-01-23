package com.yanshen.admin.clients.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yanshen.common.core.annotation.Excel;
import com.yanshen.common.core.annotation.Excel.ColumnType;
import com.yanshen.common.core.annotation.Excel.Type;
import com.yanshen.common.core.annotation.Excels;
import com.yanshen.common.core.web.domain.BaseEntity;


import com.yanshen.common.core.xss.Xss;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author Yanshen
 */
@ApiModel(value = "SysUser", description = "用户对象")
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId("user_id")
    @Excel(name = "用户序号", cellType = ColumnType.NUMERIC)
    private Long userId;


    /**
     * 用户类型（0系统用户 1外部用户 2 ,信用用户）
     */
    private Integer userType;

    /**
     * 尽调机构ID
     */
    @TableField(exist = false)
    private List<String> bindOrgIds;

    /**
     * 部门ID
     */
    @NotNull
    @Excel(name = "部门编号", type = Type.IMPORT)
//    //雪花算法生成的id长度有19位，而json在序列化中会将数字类型转换为16位，这样后面的精度就丢失了,因此需要添加该注解返回前端json时保留精度
//    @JsonSerialize(using= ToStringSerializer.class)
    private Long deptId;

    /**
     * 渠道ID
     */
    @Excel(name = "渠道编号", type = Excel.Type.IMPORT)
    private String channelId;

    /**
     * 用户账号
     */
    @Excel(name = "登录名称")
    @ApiModelProperty(value = "用户账号")
    private String userName;


    /**
     * 用户昵称
     */
    @Excel(name = "用户名称")
    @ApiModelProperty(value = "用户名称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @Excel(name = "用户邮箱")
    @ApiModelProperty(value = "用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码")
    @ApiModelProperty(value = "手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    @ApiModelProperty(value = "用户性别,0=男,1=女,2=未知")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像地址")
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @Excel(name = "帐号状态", readConverterExp = "0=正常,1=停用")
    @ApiModelProperty(value = "帐号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "营销编码")
    private String saleCode;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP", type = Type.EXPORT)
    @ApiModelProperty(value = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss", type = Type.EXPORT)
    @ApiModelProperty(value = "最后登录时间")
    private Date loginDate;

//    /**
//     * 部门对象
//     */
//    @Excels({
//            @Excel(name = "部门名称", targetAttr = "deptName", type = Type.EXPORT),
//            @Excel(name = "部门负责人", targetAttr = "leader", type = Type.EXPORT)
//    })
//    @TableField(exist = false)
//    @ApiModelProperty(value = "部门对象")
//    private SysDept dept;

//    /**
//     * 角色对象
//     */
//    @TableField(exist = false)
//    @ApiModelProperty(value = "角色对象")
//    private List<SysRole> roles;

    /**
     * 角色组
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "角色组")
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "岗位组")
    private Long[] postIds;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID")
    @TableField(exist = false)
    private Long roleId;

    /**
     * 关联客户ID
     */
    @ApiModelProperty(value = "关联客户ID")
    @TableField(exist = false)
    private List<String> bindIds;

    /**
     * 角色权限字符
     */
    @ApiModelProperty(value = "角色权限字符")
    @TableField(exist = false)
    private List<String> roleKeys;

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

    public List<String> getRoleKeys() {
        return roleKeys;
    }

    public void setRoleKeys(List<String> roleKeys) {
        this.roleKeys = roleKeys;
    }

    public List<String> getBindIds() {
        return bindIds;
    }

    public void setBindIds(List<String> bindIds) {
        this.bindIds = bindIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Xss(message = "用户昵称不能包含脚本字符")
    @Size(min = 0, max = 30, message = "用户昵称长度不能超过30个字符")
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Xss(message = "用户账号不能包含脚本字符")
    @NotBlank(message = "用户账号不能为空")
    @Size(min = 0, max = 30, message = "用户账号长度不能超过30个字符")
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Email(message = "邮箱格式不正确")
    @Size(min = 0, max = 50, message = "邮箱长度不能超过50个字符")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Size(min = 0, max = 11, message = "手机号码长度不能超过11个字符")
    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSaleCode() {
        return saleCode;
    }

    public void setSaleCode(String saleCode) {
        this.saleCode = saleCode;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }



    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long[] getPostIds() {
        return postIds;
    }

    public void setPostIds(Long[] postIds) {
        this.postIds = postIds;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public List<String> getBindOrgIds() {
        return bindOrgIds;
    }

    public void setBindOrgIds(List<String> bindOrgIds) {
        this.bindOrgIds = bindOrgIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("deptId", getDeptId())
                .append("userName", getUserName())
                .append("nickName", getNickName())
                .append("email", getEmail())
                .append("phonenumber", getPhonenumber())
                .append("sex", getSex())
                .append("avatar", getAvatar())
                .append("password", getPassword())
                .append("status", getStatus())
                .append("delFlag", getDelFlag())
                .append("loginIp", getLoginIp())
                .append("loginDate", getLoginDate())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}

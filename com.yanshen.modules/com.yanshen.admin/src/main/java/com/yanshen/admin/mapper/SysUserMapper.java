package com.yanshen.admin.mapper;

import com.yanshen.admin.domain.SysUser;
import com.yanshen.admin.domain.vo.SysUserVo;

import com.yanshen.common.core.web.mapper.BaseMapperPlus;
import org.apache.ibatis.annotations.Mapper;

/**
 *  Mapper 接口
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Mapper
public interface SysUserMapper extends BaseMapperPlus<SysUserMapper,SysUser,SysUserVo> {

}

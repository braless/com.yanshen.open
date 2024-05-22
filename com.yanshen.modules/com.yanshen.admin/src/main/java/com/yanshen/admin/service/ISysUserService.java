package com.yanshen.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.admin.domain.dto.SysUserDto;
import com.yanshen.admin.domain.vo.SysUserVo;
import com.yanshen.common.core.web.page.PageQuery;


import java.util.List;

/**
 *  服务类
 *
 * @author Yanshen
 * @since 2024-05-22
 */
public interface ISysUserService {

    /**
     * 查询列表-分页
     *
     * @param dto
     * @return sysUser集合
     */
    Page<SysUserVo> queryPageList(SysUserDto dto, PageQuery pageQuery);

    /**
     * 查询列表
     *
     * @param dto
     * @return sysUser集合
     */
    List<SysUserVo> queryList(SysUserDto dto);

    /**
     * 查询
     *
     * @param id ID
     * @return SysUserVo
     */
    SysUserVo selectSysUserById(Long id);

    /**
     * 批量删除
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    boolean deleteSysUserByIds(Long[] ids);

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    boolean deleteSysUserById(Long id);
    /**
     * 新增
     *
     * @param dto 
     * @return 结果
     */
    boolean insertSysUser(SysUserDto dto);

    /**
     * 修改
     *
     * @param dto 
     * @return 结果
     */
    boolean updateSysUser(SysUserDto dto);
    }

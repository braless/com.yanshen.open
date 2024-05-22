package com.yanshen.admin.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.admin.domain.SysUser;
import com.yanshen.admin.domain.dto.SysUserDto;
import com.yanshen.admin.domain.vo.SysUserVo;
import com.yanshen.admin.mapper.SysUserMapper;
import com.yanshen.admin.service.ISysUserService;
import com.yanshen.common.core.web.page.PageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 *  服务实现类
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl implements ISysUserService {

    private final SysUserMapper baseMapper;

    /**
     * 查询列表-分页
     *
     * @param dto 
     * @return sysuser集合
     */
    @Override
    public Page<SysUserVo> queryPageList(SysUserDto dto, PageQuery pageQuery) {
        LambdaQueryWrapper<SysUser> lqw = buildQueryWrapper(dto);
        Page<SysUserVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return result;
     }

    /**
     * 查询列表
     *
     * @param dto 
     * @return sysuser集合
     */
    @Override
    public List<SysUserVo> queryList(SysUserDto dto)
        {
        LambdaQueryWrapper<SysUser> lqw = buildQueryWrapper(dto);
        return baseMapper.selectVoList(lqw);
    }
    /**
     * 查询
     *
     * @param id ID
     * @return SysUserVo
     */
    @Override
    public SysUserVo selectSysUserById(Long id)
    {
        return baseMapper.selectVoById(id);
    }

    /**
     * 批量删除
     *
     * @param ids 需要删除的ID
     * @return 结果
     */
    @Override
    public boolean deleteSysUserByIds(Long[] ids)
    {
        List<Long> longs = Arrays.asList(ids);
        return baseMapper.deleteBatchIds(longs)>0;
    }

    /**
     * 删除信息
     *
     * @param id ID
     * @return 结果
     */
    @Override
    public boolean deleteSysUserById(Long id)
    {
        return baseMapper.deleteById(id)> 0;
    }

    /**
     * 新增
     *
     * @param dto 
     * @return 结果
     */
    @Override
    public boolean insertSysUser(SysUserDto dto)
    {
        SysUser add = BeanUtil.copyProperties(dto, SysUser.class);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if (add != null) {
                //按需选择将id放入dto实体
                //dto.setId(add.getId());
            } else {
                return false;
            }
        }
        return flag;
    }

    /**
     * 修改
     *
     * @param dto 
     * @return 结果
     */
    @Override
    public boolean updateSysUser(SysUserDto dto)
    {
        SysUser update = BeanUtil.copyProperties(dto, SysUser.class);
        return baseMapper.updateById(update)> 0;
    }
    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(SysUser entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 自定义查询条件，按照条件自己定义
     */
    private LambdaQueryWrapper<SysUser> buildQueryWrapper(SysUserDto dto) {
       Map<String, Object> params = dto.getParams();
       LambdaQueryWrapper<SysUser> lqw = Wrappers.lambdaQuery();
       return lqw;
    }
}

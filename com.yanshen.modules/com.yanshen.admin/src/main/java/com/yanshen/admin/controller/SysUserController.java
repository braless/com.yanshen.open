package com.yanshen.admin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.admin.domain.dto.SysUserDto;
import com.yanshen.admin.domain.vo.SysUserVo;
import com.yanshen.admin.service.ISysUserService;
import com.yanshen.common.core.domain.R;
import com.yanshen.common.core.web.page.PageQuery;
import com.yanshen.common.log.annotation.Log;
import com.yanshen.common.log.enums.BusinessType;
import com.yanshen.common.web.BasePlusController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 前端控制器
 *
 * @author Yanshen
 * @since 2024-05-22
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/sysUser")
@Api(tags = "用户管理")
public class SysUserController extends BasePlusController {

    private final ISysUserService sysUserService;

    /**
     * 查询列表-分页
     */
    //@SaCheckPermission("")
    @GetMapping("/listPage")
    @ApiOperation(value = "查询列表-分页")
    public R<Page<SysUserVo>> list(SysUserDto dto, PageQuery pageQuery) {
        Page<SysUserVo> resultPage = sysUserService.queryPageList(dto, pageQuery);
        return R.ok(resultPage);
    }

    /**
     * 查询列表-不分页
     */
    //@SaCheckPermission("")
    @GetMapping("/list")
    @ApiOperation(value = "查询列表-不分页")
    public R<List<SysUserVo>> list(SysUserDto dto) {
        List<SysUserVo> list = sysUserService.queryList(dto);
        return R.ok(list);
    }

    /**
     * 新增
     */
    //@SaCheckPermission("")
    @Log(title = "", businessType = BusinessType.INSERT)
    @PostMapping
    @ApiOperation(value = "新增")
    public R<Void> add(@RequestBody SysUserDto dto) {
        return toAjax(sysUserService.insertSysUser(dto));
    }

    /**
     * 修改
     */
    //@SaCheckPermission("")
    @Log(title = "", businessType = BusinessType.UPDATE)
    @PutMapping
    @ApiOperation(value = "修改")
    public R<Void> edit(@RequestBody SysUserDto dto) {
        return toAjax(sysUserService.updateSysUser(dto));
    }

    /**
     * 获取详细信息
     */
    //@SaCheckPermission("")
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取详细信息")
    public R<SysUserVo> getInfo(@NotNull(message = "ID不能为空") @PathVariable("id") Long id) {
        return R.ok(sysUserService.selectSysUserById(id));
    }

    /**
     * 删除
     */
    //@SaCheckPermission("")
    @Log(title = "", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除")
    public R<Void> remove(@NotNull(message = "ID不能为空") @PathVariable Long[] ids) {
        return toAjax(sysUserService.deleteSysUserByIds(ids));
    }

    /**
     * 状态修改
     */
    //@SaCheckPermission("")
    @Log(title = "", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ApiOperation(value = "状态修改")
    public R<Void> changeStatus(@RequestBody SysUserDto dto) {
        return toAjax(sysUserService.updateSysUser(dto));
    }

}

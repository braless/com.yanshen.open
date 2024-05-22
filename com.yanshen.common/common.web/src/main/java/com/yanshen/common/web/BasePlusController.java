package com.yanshen.common.web;


import com.yanshen.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;

/**
 * web层通用数据处理
 *
 * @author Lion Li
 */
@Slf4j
public class BasePlusController {

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果
     */
    protected R<Void> toAjax(int rows) {
        return rows > 0 ? R.ok() : R.fail();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果
     * @return 操作结果
     */
    protected R<Void> toAjax(boolean result) {
        return result ? R.ok() : R.fail();
    }

    /**
     * 返回失败消息
     */
    public R<Void> error() {
        return R.fail("操作失败");
    }

    /**
     * 返回失败消息
     */
    public R<Void> error(String message) {
        return R.fail(message);
    }
}

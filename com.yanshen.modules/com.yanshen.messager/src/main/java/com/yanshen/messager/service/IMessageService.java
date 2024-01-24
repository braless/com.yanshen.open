package com.yanshen.messager.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.common.core.web.page.PageQuery;
import com.yanshen.messager.domain.dto.MessageDto;
import com.yanshen.messager.domain.vo.MessageVo;

import java.util.List;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-24 09:15
 * @Description:
 * @Location: com.yanshen.messager.service
 * @Project: com.yanshen.open
 */
public interface IMessageService {
    /**
     * 分页查询
     * @param messageDto
     * @return
     */
    Page<MessageVo> pageList(MessageDto messageDto, PageQuery pageQuery);

    /**
     * 查询列表
     * @param messageDto
     * @return
     */
    List<MessageVo> queryList(MessageDto messageDto);

    /**
     * 删除
     * @param ids
     * @return
     */
    boolean deleteByIds(Long[] ids);


    boolean insert(MessageDto messageDto);

    boolean update(MessageDto messageDto);
}

package com.yanshen.messager.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.common.core.web.page.PageQuery;
import com.yanshen.common.datasource.config.DataSource;
import com.yanshen.common.datasource.config.DataSourceType;
import com.yanshen.messager.domain.Message;
import com.yanshen.messager.domain.dto.MessageDto;
import com.yanshen.messager.domain.vo.MessageVo;
import com.yanshen.messager.mapper.MessagerMapper;
import com.yanshen.messager.service.IMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-24 09:15
 * @Description:
 * @Location: com.yanshen.messager.service.impl
 * @Project: com.yanshen.open
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class IMessageServiceImpl implements IMessageService {

    private final MessagerMapper messagerMapper;
    /**
     * 分页查询
     *
     * @param messageDto
     * @param pageQuery
     * @return
     */
    @Override
    @DataSource(DataSourceType.SLAVE)
    public Page<MessageVo> pageList(MessageDto messageDto, PageQuery pageQuery) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(messageDto);
        Page<MessageVo> page = messagerMapper.selectVoPage(pageQuery.build(), lqw);
        return page;
    }

    /**
     * 查询列表
     *
     * @param messageDto
     * @return
     */
    @Override
    @DataSource(DataSourceType.MASTER)
    public List<MessageVo> queryList(MessageDto messageDto) {
        LambdaQueryWrapper<Message> lqw = buildQueryWrapper(messageDto);
        return messagerMapper.selectVoList(lqw);
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @Override
    public boolean deleteByIds(Long[] ids) {
        return false;
    }

    @Override
    public boolean insert(MessageDto messageDto) {
        Message message = BeanUtil.copyProperties(messageDto, Message.class);
        boolean flag = messagerMapper.insert(message)>0;
        return flag;
    }

    @Override
    public boolean update(MessageDto messageDto) {
        Message message = BeanUtil.copyProperties(messageDto, Message.class);
        return messagerMapper.updateById(message)>0;
    }
    /**
     * 自定义查询条件，按照条件自己定义
     */
    private LambdaQueryWrapper<Message> buildQueryWrapper(MessageDto dto) {
        Map<String, Object> params = dto.getParams();
        LambdaQueryWrapper<Message> lqw = Wrappers.lambdaQuery();
        return lqw;
    }
}

package com.yanshen.messager.mapper;

import com.yanshen.common.core.web.mapper.BaseMapperPlus;
import com.yanshen.messager.domain.vo.MessageVo;
import com.yanshen.messager.domain.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-24 09:16
 * @Description:
 * @Location: com.yanshen.messager.mapper
 * @Project: com.yanshen.open
 */
@Mapper
public interface MessagerMapper extends BaseMapperPlus<MessagerMapper, Message, MessageVo> {
}

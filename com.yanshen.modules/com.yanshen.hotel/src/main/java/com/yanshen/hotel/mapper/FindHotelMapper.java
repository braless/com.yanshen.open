package com.yanshen.hotel.mapper;

import com.yanshen.common.core.web.mapper.BaseMapperPlus;
import com.yanshen.hotel.domain.Hotel;
import com.yanshen.hotel.domain.vo.HotelVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:57
 * @Description:
 * @Location: com.yanshen.hotel.mapper
 * @Project: com.yanshen.open
 */
@Mapper
public interface FindHotelMapper extends BaseMapperPlus<FindHotelMapper, Hotel, HotelVo> {
}

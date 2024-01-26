package com.yanshen.hotel.service;

import com.yanshen.hotel.domain.vo.HotelVo;

import java.util.List;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:57
 * @Description:
 * @Location: com.yanshen.hotel.service
 * @Project: com.yanshen.open
 */
public interface FindHotelService {


    List<HotelVo> findList(HotelVo hotelVo);

    HotelVo findOne(HotelVo hotelVo);

    HotelVo findOneSlave(HotelVo hotelVo);
}

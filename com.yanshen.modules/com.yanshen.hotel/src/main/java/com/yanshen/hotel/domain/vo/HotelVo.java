package com.yanshen.hotel.domain.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:58
 * @Description:
 * @Location: com.yanshen.hotel.domain
 * @Project: com.yanshen.open
 */
@Data
@Builder
public class HotelVo {
    private Long id;

    private String hotelName;
}

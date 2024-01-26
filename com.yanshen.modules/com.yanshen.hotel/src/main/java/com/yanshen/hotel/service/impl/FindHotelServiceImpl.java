package com.yanshen.hotel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yanshen.common.ds.config.DataSource;
import com.yanshen.common.ds.config.DataSourceType;
import com.yanshen.hotel.domain.Hotel;
import com.yanshen.hotel.domain.vo.HotelVo;
import com.yanshen.hotel.mapper.FindHotelMapper;
import com.yanshen.hotel.service.FindHotelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 16:57
 * @Description:
 * @Location: com.yanshen.hotel.service.impl
 * @Project: com.yanshen.open
 */
@AllArgsConstructor
@Service
@Slf4j
public class FindHotelServiceImpl implements FindHotelService {

    private final FindHotelMapper baseMapper;

    @Override
    public List<HotelVo> findList(HotelVo hotelVo) {
        return null;
    }

    @Override
    @DataSource(DataSourceType.MASTER)
    public HotelVo findOne(HotelVo hotelVo) {
        if(System.currentTimeMillis()%2==0){
            log.info("走主库查询");
            return baseMapper.selectVoOne(new LambdaQueryWrapper<Hotel>().eq(Hotel::getId, hotelVo.getId()));
        }else {
            log.info("走从库查询");
            return this.findOneSlave(hotelVo);
        }
    }
    @DataSource(DataSourceType.SLAVE)
    @Override
    public HotelVo findOneSlave(HotelVo hotelVo) {
        return baseMapper.selectVoOne(new LambdaQueryWrapper<Hotel>().eq(Hotel::getId, hotelVo.getId()));
    }
}

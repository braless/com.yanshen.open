package com.yanshen.hotel.controller;

import com.yanshen.common.core.response.R;
import com.yanshen.hotel.domain.vo.HotelVo;
import com.yanshen.hotel.feign.AuthClient;
import com.yanshen.hotel.service.FindHotelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-22 15:45
 * @Description:
 * @Location: com.yanshen.hotel.controller
 * @Project: com.yanshen.open
 */
@RequestMapping("/hotel")
@RestController
@Slf4j
@Api(value = "酒店管理接口",tags = "酒店信息接口类")
@RequiredArgsConstructor
public class HotelController {

//    @Autowired
//    private AuthClient authClient;
    private final FindHotelService findHotelService;
    @GetMapping("/find/{id}")
    @ApiOperation(value = "查询酒店")
    public R<HotelVo> findOne(@PathVariable Long id){
        log.info("查询酒店:{}",id);
        HotelVo one;
        //authClient.auth("1","1");
        if(System.currentTimeMillis()%2==0){
            one = findHotelService.findOne(HotelVo.builder().id(id).build());
        }else {
            one=findHotelService.findOneSlave(HotelVo.builder().id(id).build());
        }
        return R.ok(one);
    }

}

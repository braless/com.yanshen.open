package com.yanshen.controller;


import com.yanshen.base.ApiResult;
import com.yanshen.listener.DevDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cyc
 * @Date: 2023/3/27 18:47
 * @Description:
 */
@RequestMapping("/server")
@RestController
public class OrderController {



    @RequestMapping("/info")
    public ApiResult getOrder(){
        DevDTO devDTO=new DevDTO();
        devDTO.setDevName("本消息由OrderService返回");
        return ApiResult.success(devDTO,"本消息由OrderService返回");
    }
}

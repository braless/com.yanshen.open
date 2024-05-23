package com.yanshen.chat.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Yanchao
 * @Date: 2024/5/22 下午4:11
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Api("JSR303协议")
@RestController
@RequestMapping("web/test")
public class ChatController {
    @ApiOperation("test")
    @RequestMapping("test")
    public String test(){
        return "test";
    }
}

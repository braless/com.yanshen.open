package com.yanshen.messager.controller;

import cn.hutool.http.HttpRequest;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.common.core.response.R;
import com.yanshen.common.core.web.page.PageQuery;
import com.yanshen.messager.domain.dto.MessageDto;
import com.yanshen.messager.domain.vo.MessageVo;
import com.yanshen.messager.service.IMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-18 15:42
 * @Description:
 * @Location: com.yanshen.messager.controller
 * @Project: com.yanshen.open
 */
@Slf4j
@RestController
@RequestMapping("/msg")
@Api(value = "用户管理接口",tags = "用户信息接口类")
@RequiredArgsConstructor
public class MsgController {

    private final IMessageService messageService;
    /**
     *
     * @return
     */
    @GetMapping("/send")
    public R<?> msg(@RequestParam String userName,@RequestParam String password){
        log.info("接收消息成功:{}",userName);
        //SmsClientUtil.sendPush("13992608022","【研神科技】您的验证码是：123456。请不要把验证码泄露给其他人。如非本人操作，可不用理会！");
        String title= "研神科技";
        String desc="您的验证码是：123456。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
        HttpRequest.get("https://api.day.app/Y3pFpzvTtfhyaJxeAQtXGR/" + title + "/" + desc + "/?icon=https://cdn.jsdelivr.net/gh/braless/site_logo/Bittorrent.png").execute();
        return R.ok("this is ok");
    }

    /**
     * 分页查询
     * @param dto
     * @param pageQuery
     * @return
     */
    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public R<Page<MessageVo>> page(MessageDto dto, PageQuery pageQuery){
        return R.ok(messageService.pageList(dto,pageQuery));
    }
}

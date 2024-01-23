package com.yanshen.messager.controller;

import cn.hutool.http.HttpRequest;
import com.yanshen.common.core.response.R;
import com.yanshen.common.core.util.SmsClientUtil;
import com.yanshen.messager.domain.Messager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
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
public class MsgController {
    /**
     *
     * @return
     */
    @RequestMapping("/send")
    public R<?> msg(@RequestParam String userName,@RequestParam String password){
        log.info("接收消息成功:{}",userName);
        //SmsClientUtil.sendPush("13992608022","【研神科技】您的验证码是：123456。请不要把验证码泄露给其他人。如非本人操作，可不用理会！");
        String title= "研神科技";
        String desc="您的验证码是：123456。请不要把验证码泄露给其他人。如非本人操作，可不用理会！";
        HttpRequest.get("https://api.day.app/Y3pFpzvTtfhyaJxeAQtXGR/" + title + "/" + desc + "/?icon=https://cdn.jsdelivr.net/gh/braless/site_logo/Bittorrent.png").execute();
        return R.ok("this is ok");
    }
}

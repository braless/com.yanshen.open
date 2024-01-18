package com.yanshen.messager.controller;

import com.yanshen.core.response.R;
import com.yanshen.messager.domain.Messager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public R<?> msg(@RequestBody Messager messager){
        log.info("接收消息成功:{}",messager);
        return R.ok("this is ok");
    }
}

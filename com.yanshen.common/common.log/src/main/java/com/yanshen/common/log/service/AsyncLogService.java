package com.yanshen.common.log.service;

import com.yanshen.common.log.domain.OperLogDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步调用日志服务
 *
 * @author zksk
 */
@Service
@Slf4j
public class AsyncLogService {
//    @Autowired(required = false)
//    private RemoteLogService remoteLogService;
//
//    @Autowired(required = false)
//    private RemoteChannelLogService remoteChannelLogService;

    /**
     * 保存系统日志记录
     */
    @Async
    public void saveSysLog(OperLogDto operLogDto) {
        //TODO
        log.info("日志服务存储操作日志:{}", operLogDto);
        //remoteLogService.saveLog(operLogDto);
    }

//    @Async
//    public void saveChannelLog(String orgId, ChannelLogDto channelLogDto) {
////        String parameters = Base64StrHook.hookStr(channelLogDto.getParameters());
////        String returnBody = Base64StrHook.hookStr(channelLogDto.getReturnBody());
////        channelLogDto.setParameters(parameters);
////        channelLogDto.setReturnBody(returnBody);
////        remoteChannelLogService.saveLog(orgId, channelLogDto, SecurityConstants.INNER);
//    }
}

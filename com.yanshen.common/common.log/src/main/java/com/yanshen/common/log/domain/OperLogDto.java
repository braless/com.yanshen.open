package com.yanshen.common.log.domain;

import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2024-01-26 18:02
 * @Description:
 * @Location: com.yanshen.common.log.domain
 * @Project: com.yanshen.open
 */
@Data
public class OperLogDto {


    private String operName;
    private String operIp;
    private Integer status;
    private String operUrl;
    private String title;
    private Integer businessType;
    private String errorMsg;
    private Integer operatorType;
    private String jsonResult;
    private String requestMethod;
    private String method;
    private String operParam;


}

package com.yanshen.base;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Getter
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    INVALID_PARAMS(400, "请求参数格式错误"),
    UNAUTHORIZED(401, "认证失败"),
    FORBIDDEN(403, "没有相关权限"),
    RESOURCES_NOT_EXIST(404, "访问的资源不存在"),
    VALIDATE_FAILED(412, "请求参数值错误"),
    TOO_LARGE(413, "请求内容过大"),
    SERVICE_DEGRADATION(444, "服务降级-该资源暂时无法访问，请稍后再试"),
    FAILED(500, "操作失败"),
    REQUEST_METHOD_NOT_SUPPORTED(10008, "请求方式(Method)不支持"),
    REQUEST_TYPE_NOT_SUPPORTED(10009, "请求方式(Content-Type)不支持"),


    MOBILE_EXIST(1001,"手机号码已存在"),
    ENCREPTEDDATA_NULL(1002,"encrepteddata参数为空"),
    IV_NULL(1003,"iv参数为空"),
    CODE_NULL(1004,"code参数为空"),
    USER_NULL(1005,"无会员信息"),
    MOBILE_GAIN_FAIL(1006,"手机号码获取失败"),
    TYPE_NULL(1007,"TYPE参数为空"),
    LOGINCODE_NULL(1008,"LOGINCODE参数为空"),
    MOBILEORUSERNO_NULL(1009,"mobileOrUserNo参数为空"),
    USERID_NULL(1010,"userId参数为空"),
    TYPE_ERROR(1011,"type类型不对"),
    USER_EXIST(1012,"用户已存在"),
    MODIFY_FAIL(1013,"修改失败"),
    LLLEGAL_PARAMS(1014,"[%s]参数不合法"),
    PARAMS_NULL(1014,"[%s]参数为空"),
    //微信相
    CODE_INVALID(40029,"code 无效"),
    CODE_IS_USED(40163,"code已被使用，请换其他code"),
    FREQUENCY_LIMITATION(45011,"频率限制，每个用户每分钟100次");


    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ResultCode getMessageByCode(Integer code){
        if(null == code){
            return null;
        }
        for(ResultCode resultCodes : ResultCode.values()){
            if(code.longValue() == resultCodes.code){
                return resultCodes;
            }
        }
        return ResultCode.FAILED;
    }

}

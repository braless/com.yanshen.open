package com.yanshen.core.response;


import com.yanshen.core.constants.Constants;
import com.yanshen.core.constants.ReturnMsgConstants;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "R", description = "统一返回对象")
public class R<T> implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = Constants.SUCCESS;
    /**
     * 失败
     */
    public static final int FAIL = Constants.FAIL;
    private static final long serialVersionUID = 1L;
    //@ApiModelProperty("状态码")
    private int code;

    //@ApiModelProperty("消息")
    private String msg;

    //@ApiModelProperty("数据")
    private T data;

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, ReturnMsgConstants.SUCCESS);
    }

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, ReturnMsgConstants.SUCCESS);
    }

    public static <T> R<T> ok(T data, String msg) {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> ok(String msg) {
        return restResult(null, SUCCESS, msg);
    }

    public static <T> R<T> fail() {
        return restResult(null, FAIL, ReturnMsgConstants.FAIL);
    }

    public static <T> R<T> fail(String msg) {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data) {
        return restResult(data, FAIL, ReturnMsgConstants.FAIL);
    }

    public static <T> R<T> fail(T data, String msg) {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> Boolean isError(R<T> ret) {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret) {
        return R.SUCCESS == ret.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

package exception;


import base.IErrorCode;

public class CustomException extends RuntimeException {

    private String errorMsg;

    private long code = 400;

    public CustomException() {
        super();
    }

    public CustomException(IErrorCode resultCode) {
        super(resultCode.getMessage());
        this.errorMsg = resultCode.getMessage();
        this.code = resultCode.getCode();
    }

    public CustomException(IErrorCode resultCode, String errorMsg) {
        super(resultCode.getMessage());
        this.errorMsg = errorMsg;
        this.code = resultCode.getCode();
    }

    public CustomException(String errorMsg, Object... args) {
        super(String.format(errorMsg, args));
        this.errorMsg = String.format(errorMsg, args);
    }

    public CustomException(long code, String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public long getCode() {
        return code;
    }

    public CustomException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}

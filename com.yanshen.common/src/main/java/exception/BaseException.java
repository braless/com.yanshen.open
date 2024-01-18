package exception;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 15:36
 **/
public class BaseException extends RuntimeException{
    private String errorNo;
    private String errorInfo;
    private Throwable cause;

    public BaseException(Throwable cause){
        this.cause = cause;
    }

    public BaseException(String errorNo){
        this.setErrorNo(errorNo);
    }

    public BaseException(String errorNo, String errorInfo){
        super();
        this.setErrorNo(errorNo);
        this.setErrorInfo(errorInfo);
    }

    public BaseException(String errorNo, String errorInfo, Throwable cause){
        super();
        this.setErrorNo(errorNo);
        this.setErrorInfo(errorInfo);
        this.setCause(cause);
    }

    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    public String getErrorNo() {
        return errorNo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public String getMessage(){
        return errorInfo;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}

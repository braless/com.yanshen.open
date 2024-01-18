package exception;

/**
 * @Auther: taohaifeng
 * @Date: 2018/11/1 15:39
 * @Description: 检查抛错异常类
 * @since: 4.0
 */
public class CheckException extends RuntimeException {
	public CheckException() {
	}

	public CheckException(String message) {
		super(message);
	}

	public CheckException(Throwable cause) {
		super(cause);
	}

	public CheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}

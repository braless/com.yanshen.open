package exception;

public class OtherException extends RuntimeException {

	private static final long serialVersionUID = 7827444065800695635L;
	private String code;


	public OtherException(String message) {
		super(message);
		this.code = "100000";
	}

	public OtherException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	
}
package exception;

public class TipException extends RuntimeException {

	private static final long serialVersionUID = 7827444065800695635L;
	private String code;


	public TipException(String message) {
		super(message);
		this.code = "100000";
	}
	
	public TipException(String code, String message) {
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
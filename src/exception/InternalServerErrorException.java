package exception;

public class InternalServerErrorException extends Exception {

	private int code;

	public InternalServerErrorException(String message, int code) {
		super(message);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

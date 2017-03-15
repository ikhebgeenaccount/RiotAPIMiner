package exception;

/**
 * The type Http status exception.
 */
public class HTTPStatusException extends Exception {

	private int code;

	/**
	 * Instantiates a new Http status exception.
	 *
	 * @param s the s
	 */
	public HTTPStatusException(String s) {
		super(s);
	}

	/**
	 * Instantiates a new Http status exception.
	 *
	 * @param s    the s
	 * @param code the code
	 */
	public HTTPStatusException(String s, int code) {
		super(s);
		this.code = code;
	}

	/**
	 * Gets the HTTP status code.
	 *
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
}

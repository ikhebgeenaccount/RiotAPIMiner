package com.ihga.exception;

/**
 * The type Rate limit exceeded com.ihga.exception.
 */
public class RateLimitExceededException extends Exception {

	private int secondsLeftToReset;

	/**
	 * Instantiates a new Rate limit exceeded com.ihga.exception.
	 *
	 * @param s                  the s
	 * @param secondsLeftToReset the seconds left to reset
	 */
	public RateLimitExceededException(String s, int secondsLeftToReset) {
		super(s);
		this.secondsLeftToReset = secondsLeftToReset;
	}

	/**
	 * Gets seconds left to reset.
	 *
	 * @return the seconds left to reset
	 */
	public int getSecondsLeftToReset() {
		return secondsLeftToReset;
	}
}

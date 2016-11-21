package com.kcom.javatest.exception;

/**
 * This is a generic exception class to return all the exceptions which returning
 * change for the coins. All exceptions caught will through this exeption.
 */
public class ChangeNotGivenException extends Exception {
	public ChangeNotGivenException(String message) {
		super(message);
	}
	public ChangeNotGivenException(String message, Exception e) {
		super(message, e);
	}
}

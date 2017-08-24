package com.qianlixy.framework.upload.exception;

public class InvalidFileSizeException extends RuntimeException {

	private static final long serialVersionUID = 7237195273811395987L;

	public InvalidFileSizeException() {
		super();
	}

	public InvalidFileSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidFileSizeException(String message) {
		super(message);
	}

	public InvalidFileSizeException(Throwable cause) {
		super(cause);
	}

}

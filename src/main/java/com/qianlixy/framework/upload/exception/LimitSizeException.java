package com.qianlixy.framework.upload.exception;

public class LimitSizeException extends RuntimeException {

	private static final long serialVersionUID = -7257830593134238588L;

	public LimitSizeException() {
		super();
	}

	public LimitSizeException(String message, Throwable cause) {
		super(message, cause);
	}

	public LimitSizeException(String message) {
		super(message);
	}

	public LimitSizeException(Throwable cause) {
		super(cause);
	}

}

package com.mine.qrcode.common.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer status;

	private String message;

	public ServiceException(Integer status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
		this.message = message;
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

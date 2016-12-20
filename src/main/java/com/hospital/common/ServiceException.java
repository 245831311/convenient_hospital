package com.hospital.common;

/**
 * 
 * 服务调用异常
 * 
 *
 */
public class ServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4669790177351518819L;

	private int errorCode;
	private String errorMessage;

	public ServiceException(String msg, int errorCode, String errorMessage) {
		super(msg);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public ServiceException(String msg, Throwable t, int errorCode, String errorMessage) {
		super(msg, t);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	
}

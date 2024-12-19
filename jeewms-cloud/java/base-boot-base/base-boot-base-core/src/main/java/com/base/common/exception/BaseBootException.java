package com.base.common.exception;

public class BaseBootException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public BaseBootException(String message){
		super(message);
	}
	
	public BaseBootException(Throwable cause)
	{
		super(cause);
	}
	
	public BaseBootException(String message, Throwable cause)
	{
		super(message,cause);
	}
}

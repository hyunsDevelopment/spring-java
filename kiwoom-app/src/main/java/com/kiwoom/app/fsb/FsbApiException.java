package com.kiwoom.app.fsb;

import java.io.Serial;

public class FsbApiException extends Exception {
	
	@Serial
	private static final long serialVersionUID = 5128391396532871018L;
	
	private final int ERROR_CODE;
	
	public FsbApiException(int errorCode, String msg) {
		super(msg);
		ERROR_CODE = errorCode;
	}

	public FsbApiException(String msg) {
		this(999,msg);
	}
	
	public int getErrorCode(){
		return ERROR_CODE;
	}
	
	public String errorResponse(FsbApiException e) {
		return "{\"code\":"+ e.getErrorCode() + "\"msg\":\"" + e.getMessage().replaceAll("\r", "\\\\r").replaceAll("\n", "\\\\n") + "\"}"; 
	}
}

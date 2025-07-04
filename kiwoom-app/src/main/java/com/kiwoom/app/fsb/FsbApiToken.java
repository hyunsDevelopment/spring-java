package com.kiwoom.app.fsb;

public class FsbApiToken {
	
	private String tokenValue = null;
	
	private FsbApiToken() {

	}
	
	private static class Holder {
		public static final FsbApiToken INSTANCE = new FsbApiToken();
	}
	
	public static FsbApiToken getInstance() {
		return Holder.INSTANCE;
	}
	
	public void setTokenValue(String value) {
		FsbApiToken.getInstance().tokenValue = value;
	}
	
	public String getTokenValue() {
		return FsbApiToken.getInstance().tokenValue;
	}
}

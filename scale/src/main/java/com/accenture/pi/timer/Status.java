package com.accenture.pi.timer;

public class Status {

	private String status = "no status";
	
	public static final String EMPTY = "EMPTY";
	public static final String OVERWEIGHT = "OVERWEIGHT";
	public static final String NEGATIVE = "NEGATIVE";

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

package com.stackroute.datamunger.query;

public class Header {
	
	private String[] headers;

	/*
	 * This class should contain a member variable which is a String array, to hold
	 * the headers and should override toString() method as well.
	 */
	
	public Header(String[] headers) {
		super();
		this.headers = headers;
	}
	
	public String[] getHeaders() {
		return headers;
	}
	
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}
}

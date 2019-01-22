package com.beehive.infrastructure.payload;

public class ValueResponse {
	
	private Long id;
	private String value;
	
	public ValueResponse() {
		
	}
	
	public ValueResponse(Long id, String value) {
		this.id = id;
		this.value = value;
	}
	
	public long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
}

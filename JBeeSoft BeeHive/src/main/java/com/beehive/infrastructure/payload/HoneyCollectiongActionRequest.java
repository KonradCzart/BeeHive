package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotNull;

public class HoneyCollectiongActionRequest extends ActionRequest{
	
	@NotNull
	private Long honeyTypeId;
	
	private Double honeyAmount;
	
	public Long getHoneyTypeId() {
		return honeyTypeId;
	}
	
	public void setHoneyTypeId(Long honeyTypeId) {
		this.honeyTypeId = honeyTypeId;
	}
	
	public Double getHoneyAmount() {
		return honeyAmount;
	}
	
	public void setHoneyAmount(Double honeyAmount) {
		this.honeyAmount = honeyAmount;
	}
		
}

package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotNull;

public class QueenChangingActionRequest extends ActionRequest {
	
	@NotNull
	private Double price;

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}

package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TreatmentActionRequest extends ActionRequest{
	
	@NotBlank
	private String deseaseType;
	
	@NotBlank
	private String appliedMedicine;
	
	@NotNull
	private Double dose;
	
	@NotNull
	private Double price;

	public String getDeseaseType() {
		return deseaseType;
	}

	public void setDeseaseType(String deseaseType) {
		this.deseaseType = deseaseType;
	}

	public String getAppliedMedicine() {
		return appliedMedicine;
	}

	public void setAppliedMedicine(String appliedMedicine) {
		this.appliedMedicine = appliedMedicine;
	}

	public Double getDose() {
		return dose;
	}

	public void setDose(Double dose) {
		this.dose = dose;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
}

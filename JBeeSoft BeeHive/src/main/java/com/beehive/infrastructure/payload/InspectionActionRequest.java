package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotNull;

public class InspectionActionRequest extends ActionRequest{

	@NotNull
	private Boolean isMaggotPresent;
	
	@NotNull
	private Boolean isLairPresent;
	
	@NotNull
	private Integer hiveStrength;
	
	@NotNull
	private Integer framesWithWaxFoundation;
	
	@NotNull
	private String decription;
	

	public Boolean getIsMaggotPresent() {
		return isMaggotPresent;
	}

	public void setIsMaggotPresent(Boolean isMaggotPresent) {
		this.isMaggotPresent = isMaggotPresent;
	}

	public Boolean getIsLairPresent() {
		return isLairPresent;
	}

	public void setIsLairPresent(Boolean isLairPresent) {
		this.isLairPresent = isLairPresent;
	}

	public Integer getHiveStrength() {
		return hiveStrength;
	}

	public void setHiveStrength(Integer hiveStrength) {
		this.hiveStrength = hiveStrength;
	}

	public Integer getFramesWithWaxFoundation() {
		return framesWithWaxFoundation;
	}

	public void setFramesWithWaxFoundation(Integer framesWithWaxFoundation) {
		this.framesWithWaxFoundation = framesWithWaxFoundation;
	}

	public String getDecription() {
		return decription;
	}

	public void setDecription(String decription) {
		this.decription = decription;
	}
	
}

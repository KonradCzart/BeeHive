package com.beehive.infrastructure.payload;

public class AvailabilityResponse {
	
    private Boolean isAvailable;
   
    public AvailabilityResponse(Boolean isAvailable) {
    	this.isAvailable = isAvailable;
    }

	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}

package com.beehive.infrastructure.payload;

import com.beehive.domain.honey.HoneyType;

public class HoneyCollectingActionDTO extends ActionDTO{
	
    private HoneyType honeyType;
    private Double honeyAmount;
    
    public HoneyCollectingActionDTO(Builder builder) {
    	super(builder);
		this.honeyType = builder.honeyType;
		this.honeyAmount = builder.honeyAmount;
	}
    
    public HoneyType getHoneyType() {
		return honeyType;
	}

	public void setHoneyType(HoneyType honeyType) {
		this.honeyType = honeyType;
	}

	public Double getHoneyAmount() {
		return honeyAmount;
	}


	public void setHoneyAmount(Double honeyAmount) {
		this.honeyAmount = honeyAmount;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder extends ActionDTO.Builder<Builder> {
		
	    private HoneyType honeyType;
	    private Double honeyAmount;
	    
		@Override
		protected Builder self() {
			return this;
		}
		
		public Builder withHoneyType(HoneyType honeyType) {
			this.honeyType = honeyType;
			return this;
		}
		
		public Builder withHoneyAmount(Double honeyAmount) {
			this.honeyAmount = honeyAmount;
			return this;
		}

		@Override
		public HoneyCollectingActionDTO build() {
			return new HoneyCollectingActionDTO(this);
		}	    
	    
	}
}

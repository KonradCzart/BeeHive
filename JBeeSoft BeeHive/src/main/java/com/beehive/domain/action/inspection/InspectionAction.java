package com.beehive.domain.action.inspection;

import com.beehive.domain.action.Action;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inspection_actions")

public class InspectionAction extends Action {
	
    @NotNull
    private Boolean isMaggotPresent;
    
    @NotNull
    private Boolean isLairPresent;
    
    @NotNull
    private Integer hiveStrength;
    
    @NotNull
    private Integer framesWithWaxFoundation;
    
    @NotBlank
    @Size(max = 250)
    private String decription;
    
    public InspectionAction(Builder builder) {
    	super(builder);
    	this.isMaggotPresent = builder.isMaggotPresent;
		this.isLairPresent = builder.isLairPresent;
		this.hiveStrength = builder.hiveStrength;
		this.framesWithWaxFoundation = builder.framesWithWaxFoundation;
		this.decription = builder.decription;
	}
    
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

	public static Builder builder() {
    	return new Builder();
    }
    
    public static class Builder extends Action.Builder<Builder> {
    	
    	private Boolean isMaggotPresent;
        private Boolean isLairPresent;
        private Integer hiveStrength;
        private Integer framesWithWaxFoundation;
        private String decription;

		@Override
		protected Builder self() {
			return this;
		}
		
		public Builder withIsMaggotPresent(Boolean isMaggotPresent) {
			this.isMaggotPresent = isMaggotPresent;
			return this;
		}
		
		public Builder withIsLairPresent(Boolean isLairPresent) {
			this.isLairPresent = isLairPresent;
			return this;
		}
		
		public Builder withHiveStrength(Integer hiveStrength) {
			this.hiveStrength = hiveStrength;
			return this;
		}
		
		public Builder withframesWithWaxFoundation(Integer framesWithWaxFoundation) {
			this.framesWithWaxFoundation = framesWithWaxFoundation;
			return this;
		}
		
		public Builder withDescription(String description) {
			this.decription = description;
			return this;
		}

		@Override
		public InspectionAction build() {
			return new InspectionAction(this);
		}
    	
    }
   
}
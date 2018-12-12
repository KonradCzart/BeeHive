package com.beehive.domain.action.inspection;

import com.beehive.domain.action.Action;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inspection")

public class InspectionAction extends Action {
	
    @NotNull
    private boolean isMaggotPresent;
    
    @NotNull
    private boolean isLairPresent;
    
    @NotNull
    private int hiveStrength;
    
    @NotNull
    private int framesWithWaxFoundation;
    
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
    
    public static class Builder extends Action.Builder<Builder> {
    	
    	private boolean isMaggotPresent;
        private boolean isLairPresent;
        private int hiveStrength;
        private int framesWithWaxFoundation;
        private String decription;

		@Override
		protected Builder self() {
			return this;
		}
		
		public Builder withIsMaggotPresent(boolean isMaggotPresent) {
			this.isMaggotPresent = isMaggotPresent;
			return this;
		}
		
		public Builder withIsLairPresent(boolean isLairPresent) {
			this.isLairPresent = isLairPresent;
			return this;
		}
		
		public Builder withHiveStrength(int hiveStrength) {
			this.hiveStrength = hiveStrength;
			return this;
		}
		
		public Builder withframesWithWaxFoundation(int framesWithWaxFoundation) {
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
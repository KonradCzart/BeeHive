package com.beehive.domain.action.honeycollecting;

import com.beehive.domain.action.Action;
import com.beehive.domain.honey.HoneyType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "honey_collecting_actions")
public class HoneyCollectingAction extends Action {
	
    @ManyToOne
    @JoinColumn(name = "honey_type_id")
    private HoneyType honeyType;
    
    @NotNull
    private Double honeyAmount;
    
    public HoneyCollectingAction(Builder builder) {
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


	public static class Builder extends Action.Builder<Builder> {
		
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
		public HoneyCollectingAction build() {
			return new HoneyCollectingAction(this);
		}	    
	    
	}
	
}
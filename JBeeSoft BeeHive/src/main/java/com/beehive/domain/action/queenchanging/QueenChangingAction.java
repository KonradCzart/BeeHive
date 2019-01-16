package com.beehive.domain.action.queenchanging;

import com.beehive.domain.action.Action;
import com.beehive.domain.bee.queen.BeeQueen;
import com.beehive.infrastructure.payload.QueenChangingActionDTO;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "queen_changing_actions")
public class QueenChangingAction extends Action {
    
    @NotNull
    private Double price;
    
    public QueenChangingAction() {

	}
    
    public QueenChangingAction(Builder builder) {
		super(builder);
		this.price = builder.price;
	}
    
    public static Builder builder() {
		return new Builder();
	}
    
    @Override
    public QueenChangingActionDTO.Builder builderDTO() {
    	return new QueenChangingActionDTO.Builder()
    			.withPrice(price)
    			.withActionName("Queen Changing");
    }
    
    public static class Builder extends Action.Builder<Builder> {

    	private Double price;
    	
		@Override
		protected Builder self() {
			return this;
		}

		public Builder withPrice(Double price) {
			this.price = price;
			return this;
		}
		
		@Override
		public QueenChangingAction build() {
			return new QueenChangingAction(this);
		}
    	
    }
}
package com.beehive.domain.action.queenchanging;

import com.beehive.domain.action.Action;
import com.beehive.domain.bee.queen.BeeQueen;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "queen_changing_actions")
public class QueenChangingAction extends Action {
	
//    @ManyToMany
//    private Set<BeeQueen> changedBeeQueens;
    
    @NotNull
    private int price;
    
    public QueenChangingAction(Builder builder) {
		super(builder);
		this.price = builder.price;
	}
    
    public static class Builder extends Action.Builder<Builder> {

    	private int price;
    	
		@Override
		protected Builder self() {
			return this;
		}

		public Builder withPrice(int price) {
			this.price = price;
			return this;
		}
		
		@Override
		public QueenChangingAction build() {
			return new QueenChangingAction(this);
		}
    	
    }
}
package com.beehive.domain.statistics;

import java.math.BigDecimal;

public class QuantityStatistic {
	
	private String actionName;
	private String typeName;
	private Double amount;
	private BigDecimal price;
	
	public QuantityStatistic(Builder builder) {
		this.actionName = builder.actionName;
		this.typeName = builder.typeName;
		this.amount = builder.amount;
		this.price = builder.price;
	}
	
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public static Builder builder() {
    	return new Builder();
    }
	
	public static class Builder{
		private String actionName;
		private String typeName;
		private Double amount;
		private BigDecimal price;
		
		public Builder withActionName(String actionName) {
			this.actionName = actionName;
			return this;
		}
		
		public Builder withTypeName(String typeName) {
			this.typeName = typeName;
			return this;
		}
		
		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}
		
		public Builder withPrice(double price) {
			this.price = new BigDecimal(price);
			return this;
		}
		
		public QuantityStatistic build() {
			return new QuantityStatistic(this);
		}
	}
	
	
}

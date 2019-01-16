package com.beehive.infrastructure.payload;

import java.math.BigDecimal;

public class StatisticsDTO {
	
	private String name;
	private Double amount;
	private BigDecimal averagePrice;
	private BigDecimal totalPrice;	
	
	public StatisticsDTO(Builder builder) {
		this.name = builder.name;
		this.amount = builder.amount;
		this.averagePrice = builder.averagePrice;
		this.totalPrice = builder.totalPrice;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public BigDecimal getAveragePrice() {
		return averagePrice;
	}

	public void setAveragePrice(BigDecimal averagePrice) {
		this.averagePrice = averagePrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public static Builder builder() {
    	return new Builder();
    }
	
	public static class Builder{
		private String name;
		private Double amount;
		private BigDecimal averagePrice;
		private BigDecimal totalPrice;	
		
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withAmount(Double amount) {
			this.amount = amount;
			return this;
		}
		
		public Builder withAveragePrice(BigDecimal averagePrice) {
			this.averagePrice = averagePrice;
			return this;
		}
		
		public Builder withTotalPrice(BigDecimal totalPrice) {
			this.totalPrice = totalPrice;
			return this;
		}
		
		public StatisticsDTO build() {
			return new StatisticsDTO(this);
		}
	}
	
	
	
	
}

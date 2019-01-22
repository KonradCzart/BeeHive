package com.beehive.infrastructure.payload;


public class FeedingActionDTO extends ActionDTO{
	
    private String feedType;
    private Double feedAmount;
    private Double price;
    
    public FeedingActionDTO(Builder builder) {
		super(builder);
		feedType = builder.feedType;
		feedAmount = builder.feedAmount;
		price = builder.price;
	}
    
	public String getFeedType() {
		return feedType;
	}
	
	public void setFeedType(String feedType) {
		this.feedType = feedType;
	}
	
	public Double getFeedAmount() {
		return feedAmount;
	}
	
	public void setFeedAmount(Double feedAmount) {
		this.feedAmount = feedAmount;
	}
	
	public Double getPrice() {
		return price;
	}
	
	public void setPrice(Double price) {
		this.price = price;
	}
	
	public static Builder builder() {
		return new Builder();
	}
    
    public static class Builder extends ActionDTO.Builder<Builder> {
    	
		private String feedType;
		private Double feedAmount;
		private Double price;

		@Override
		protected Builder self() {
			return this;
		}
		
		public Builder withFeedType(String feedType) {
			this.feedType = feedType;
			return this;
		}
		
		public Builder withFeedAmount(Double feedAmount) {
			this.feedAmount = feedAmount;
			return this;
		}
		
		public Builder withPrice(Double price) {
			this.price = price;
			return this;
		}
		
		@Override
		public ActionDTO build() {
			return new FeedingActionDTO(this);
		}
    	
    }
    
}

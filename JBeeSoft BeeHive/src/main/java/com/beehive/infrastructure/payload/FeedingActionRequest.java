package com.beehive.infrastructure.payload;

public class FeedingActionRequest extends ActionRequest {

	private String feedType;
	private Double feedAmount;
	private Double price;
	
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
	
}

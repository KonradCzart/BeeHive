package com.beehive.infrastructure.payload;


public class StatisticsContributorDTO {
	
	private UserDTO contributor;
	private Long incpectionActionNamber;
	private Long feedingActionNumber;
	private Long treatmentActionNumber;
	private Long honeyActionNumber;
	
	private StatisticsContributorDTO(Builder builder) {
		this.contributor = builder.contributor;
		this.incpectionActionNamber = builder.incpectionActionNamber;
		this.feedingActionNumber = builder.feedingActionNumber;
		this.treatmentActionNumber = builder.treatmentActionNumber;
		this.honeyActionNumber = builder.honeyActionNumber;
	}
	
	public UserDTO getContributor() {
		return contributor;
	}
	public void setContributor(UserDTO contributor) {
		this.contributor = contributor;
	}
	public Long getIncpectionActionNamber() {
		return incpectionActionNamber;
	}
	public void setIncpectionActionNamber(Long incpectionActionNamber) {
		this.incpectionActionNamber = incpectionActionNamber;
	}
	public Long getFeedingActionNumber() {
		return feedingActionNumber;
	}
	public void setFeedingActionNumber(Long feedingActionNumber) {
		this.feedingActionNumber = feedingActionNumber;
	}
	public Long getTreatmentActionNumber() {
		return treatmentActionNumber;
	}
	public void setTreatmentActionNumber(Long treatmentActionNumber) {
		this.treatmentActionNumber = treatmentActionNumber;
	}
	public Long getHoneyActionNumber() {
		return honeyActionNumber;
	}
	public void setHoneyActionNumber(Long honeyActionNumber) {
		this.honeyActionNumber = honeyActionNumber;
	}
	
	public static Builder builder() {
    	return new Builder();
    }
	
	public static class Builder{
		
		private UserDTO contributor;
		private Long incpectionActionNamber;
		private Long feedingActionNumber;
		private Long treatmentActionNumber;
		private Long honeyActionNumber;
		
		public Builder withContributor(UserDTO contributor) {
			this.contributor = contributor;
			return this;
		}
		
		public Builder withIncpectionActionNamber(Long incpectionActionNamber) {
			this.incpectionActionNamber = incpectionActionNamber;
			return this;
		}
		
		public Builder withFeedingActionNumber(Long feedingActionNumber) {
			this.feedingActionNumber = feedingActionNumber;
			return this;
		}
		
		public Builder withTreatmentActionNumber(Long treatmentActionNumber) {
			this.treatmentActionNumber = treatmentActionNumber;
			return this;
		}
		
		public Builder withHoneyActionNumber(Long honeyActionNumber) {
			this.honeyActionNumber = honeyActionNumber;
			return this;
		}
		
		public StatisticsContributorDTO build() {
			return new StatisticsContributorDTO(this);
		}	
	}
}

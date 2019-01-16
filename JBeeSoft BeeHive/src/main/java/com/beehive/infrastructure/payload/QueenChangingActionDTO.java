package com.beehive.infrastructure.payload;



public class QueenChangingActionDTO extends ActionDTO {
    
    private Double price;
    
    public QueenChangingActionDTO(Builder builder) {
		super(builder);
		this.price = builder.price;
	}
    
    public static Builder builder() {
		return new Builder();
	}
    
    public static class Builder extends ActionDTO.Builder<Builder> {

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
		public QueenChangingActionDTO build() {
			return new QueenChangingActionDTO(this);
		}
    	
    }
}

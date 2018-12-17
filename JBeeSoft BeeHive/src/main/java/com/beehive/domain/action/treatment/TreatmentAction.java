package com.beehive.domain.action.treatment;

import com.beehive.domain.action.Action;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "treatment_actions")

public class TreatmentAction extends Action {
	
    @NotBlank
    @Size(max = 40)
    private String deseaseType;

    @NotBlank
    @Size(max = 40)
    private String appliedMedicine;

    @NotNull
    private Double dose;

    @NotNull
    private Double price;

    public TreatmentAction(Builder builder) {
    	super(builder);
        this.deseaseType = builder.deseaseType;
        this.appliedMedicine = builder.appliedMedicine;
        this.dose = builder.dose;
        this.price = builder.price;
    }

    public String getDeseaseType() {
        return deseaseType;
    }

    public void setDeseaseType(String deseaseType) {
        this.deseaseType = deseaseType;
    }

    public String getAppliedMedicine() {
        return appliedMedicine;
    }

    public void setAppliedMedicine(String appliedMedicine) {
        this.appliedMedicine = appliedMedicine;
    }

    public Double getDose() {
        return dose;
    }

    public void setDose(Double dose) { 
    	this.dose = dose; 
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
    
    public static class Builder extends Action.Builder<Builder> {

    	private String deseaseType;
        private String appliedMedicine;
        private Double dose;
        private Double price;
        
		@Override
		protected Builder self() {
			return this;
		}
		
		public Builder withDeseaseType(String deseaseType) {
			this.deseaseType = deseaseType;
			return this;
		}
		
		public Builder withAppliedMedicine(String appliedMedicine) {
			this.appliedMedicine = appliedMedicine;
			return this;
		}
		
		public Builder withDose(Double dose) {
			this.dose = dose;
			return this;
		}
		
		public Builder withPrice(Double price) {
			this.price = price;
			return this;
		}

		@Override
		public TreatmentAction build() {
			return new TreatmentAction(this);
		}
    	
    }

}
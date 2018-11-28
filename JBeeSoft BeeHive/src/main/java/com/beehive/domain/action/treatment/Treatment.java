package com.beehive.domain.action.treatment;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Treatment extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String deseaseType;

    @NotBlank
    @Size(max = 40)
    private String appliedMedicine;

    @NotBlank
    @Size(max = 100)
    private String comment;


    public Treatment(String deseaseType, String appliedMedicine, String comment) {
        this.deseaseType = deseaseType;
        this.appliedMedicine = appliedMedicine;
        this.comment = comment;
    }

    public Long getId() {
        return id;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
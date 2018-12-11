package com.beehive.domain.action.treatment;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "treatment", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "deseaseType"
        })
})

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
    private Integer dose;

    @NotBlank
    private Integer price;

    public Treatment(String deseaseType, String appliedMedicine, Integer dose, Integer price) {
        this.deseaseType = deseaseType;
        this.appliedMedicine = appliedMedicine;
        this.dose = dose;
        this.price = price;
    }

    @OneToMany
    @JoinTable(name = "action",
            joinColumns = @JoinColumn(name = "concreteActionId"))

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

    public Integer getDose() {
        return dose;
    }

    public void setDose(Integer feedAmount) { this.dose = feedAmount; }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
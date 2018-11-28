package com.beehive.domain.action.honeyCollection;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class HoneyCollection extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String honeyType;

    @NotBlank
    private Integer honeyAmount;

    public HoneyCollection(String honeyType, Integer honeyAmount) {
        this.honeyType = honeyType;
        this.honeyAmount = honeyAmount;
    }

    public Long getId() {
        return id;
    }

    public String getHoneyType() {
        return honeyType;
    }

    public void setHoneyType(String honeyType) {
        this.honeyType = honeyType;
    }

    public Integer getHoneyAmount() {
        return honeyAmount;
    }

    public void setHoneyAmount(Integer honeyAmount) { this.honeyAmount = honeyAmount; }
}

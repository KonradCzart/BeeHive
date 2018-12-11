package com.beehive.domain.action.honeyCollection;

import com.beehive.domain.dateaudit.DateAudit;
import com.beehive.domain.userrole.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "honeyCollection")

public class HoneyCollection extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private Long honeyTypeId;

    @NotBlank
    private Integer honeyAmount;

    public HoneyCollection(Long honeyTypeId, Integer honeyAmount) {
        this.honeyTypeId = honeyTypeId;
        this.honeyAmount = honeyAmount;
    }

    @OneToMany
    @JoinTable(name = "action",
            joinColumns = @JoinColumn(name = "concreteActionId"))

    public Long getId() {
        return id;
    }

    public Long getHoneyTypeId() {
        return honeyTypeId;
    }

    public void setHoneyTypeId(Long honeyType) {
        this.honeyTypeId = honeyType;
    }

    public Integer getHoneyAmount() {
        return honeyAmount;
    }

    public void setHoneyAmount(Integer honeyAmount) { this.honeyAmount = honeyAmount; }
}

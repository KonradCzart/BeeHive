package com.beehive.domain.action.inspection;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "inspections")

public class Inspection extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String beeQueenCondition;

    @NotBlank
    @Size(max = 40)
    private String beeHiverCondition;

    @NotBlank
    @Size(max = 100)
    private String comment;

    public Inspection(String beeQueenCondition, String beeHiverCondition, String comment) {
        this.beeQueenCondition = beeQueenCondition;
        this.beeHiverCondition = beeHiverCondition;
        this.comment = comment;
    }

    @OneToMany
    @JoinTable(name = "action",
            joinColumns = @JoinColumn(name = "concreteActionId"))

    public Long getId() {
        return id;
    }

    public String getBeeQueenCondition() {
        return beeQueenCondition;
    }

    public void setBeeQueenCondition(String beeQueenCondition) {
        this.beeQueenCondition = beeQueenCondition;
    }

    public String getBeeHiverCondition() {
        return beeHiverCondition;
    }

    public void setBeeHiverCondition(String beeHiverCondition) {
        this.beeHiverCondition = beeHiverCondition;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
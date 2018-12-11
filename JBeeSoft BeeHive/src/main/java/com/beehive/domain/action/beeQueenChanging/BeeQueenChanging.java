package com.beehive.domain.action.beeQueenChanging;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "beeQueenChanging")

public class BeeQueenChanging extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private Long queenId;

    @NotBlank
    private Integer queenAge;

    @NotBlank
    private Integer price;

    public BeeQueenChanging(Long queenId, Integer queenAge, Integer price) {
        this.queenId = queenId;
        this.queenAge = queenAge;
        this.price = price;
    }

    @OneToMany
    @JoinTable(name = "action",
            joinColumns = @JoinColumn(name = "concreteActionId"))

    public Long getId() {
        return id;
    }

    public Long getQueenId() {
        return queenId;
    }

    public void setQueenId(Long queenId) {
        this.queenId = queenId;
    }

    public Integer getQueenAge() {
        return queenAge;
    }

    public void setQueenAge(Integer queenAge) {
        this.queenAge = queenAge;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
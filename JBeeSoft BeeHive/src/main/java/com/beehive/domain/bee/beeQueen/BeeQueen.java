package com.beehive.domain.action.beeQueenChanging;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "beeQueen")

public class BeeQueen extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    private Long raceId;

    @NotBlank
    @Size(max = 40)
    private String color;

    @NotBlank
    private Integer age;

    @NotBlank
    private Boolean isReproducting;

    @Size(max = 100)
    private String description;

    public BeeQueen(String name, Long raceId, String color, Integer age, Boolean isreproducting, String description) {
        this.name = name;
        this.raceId = raceId;
        this.color = color;
        this.age = age;
        this.isReproducting = isreproducting;
        this.description = description;
    }

    @OneToMany
    @JoinTable(name = "beeQueenChanging",
            joinColumns = @JoinColumn(name = "queenId")
    )

    @OneToMany
    @JoinTable(name = "hiver",
            joinColumns = @JoinColumn(name = "beeQueenId"))

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRace() {
        return raceId;
    }

    public void setRace(Long race) {
        this.raceId = race;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsReproducting() {
        return isReproducting;
    }

    public void setIsReproducting(Boolean isReproducting) {
        this.isReproducting = isReproducting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

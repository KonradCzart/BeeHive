package com.beehive.domain.action.beeQueenChanging;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "beeRace", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})

public class BeeRace extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 40)
    private String agression;

    @NotBlank
    @Size(max = 40)
    private String hivingLevel;

    @Size(max = 100)
    private String description;

    public BeeRace(String name, String agression, String hivingLevel, String description) {
        this.name = name;
        this.agression = agression;
        this.hivingLevel = hivingLevel;
        this.description = description;
    }

    @OneToOne
    @JoinTable(name = "beeQueen",
            joinColumns = @JoinColumn(name = "raceId"))

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAgression() {
        return agression;
    }

    public void setAgression(String agression) { this.agression = agression; }

    public String getHivingLevel() {
        return hivingLevel;
    }

    public void setHivingLevel(String hivingLevel) { this.hivingLevel = hivingLevel; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

}

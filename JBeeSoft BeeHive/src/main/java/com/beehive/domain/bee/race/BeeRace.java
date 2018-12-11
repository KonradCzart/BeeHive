package com.beehive.domain.bee.race;

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
    private AgressionLevel agression;

    @Size(max = 100)
    private String description;

    public BeeRace(String name, String agression, String description) {
        this.name = name;
        this.agression = AgressionLevel.valueOf(agression);
        this.description = description;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AgressionLevel getAgression() {
        return agression;
    }

    public void setAgression(String agression) { 
    	this.agression = AgressionLevel.valueOf(agression); 
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { 
    	this.description = description; 
    }

}

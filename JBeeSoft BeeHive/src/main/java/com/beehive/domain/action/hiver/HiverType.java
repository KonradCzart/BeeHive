package com.beehive.domain.action.hiver;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hiverType", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})

public class HiverType extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    private String properties;

    public HiverType(String name, String properties) {
        this.name = name;
        this.properties = properties;
    }

    @OneToOne
    @JoinTable(name = "hiver",
            joinColumns = @JoinColumn(name = "hiverTypeId"))

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getProperties() {
        return properties;
    }

    public void setProperties(String properties) { this.properties = properties; }
}

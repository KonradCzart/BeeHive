package com.beehive.domain.action.honeyCollection;

import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
@Entity
@Table(name = "honeyRype", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})

public class HoneyType extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    private Integer price;

    public HoneyType(String name, Integer price) {
        this.name = name;
        this.price = price;
    }


    @OneToOne
    @JoinTable(name = "honeyCollection",
            joinColumns = @JoinColumn(name = "honeyTypeId"))

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer honeyAmount) { this.price = price; }
}

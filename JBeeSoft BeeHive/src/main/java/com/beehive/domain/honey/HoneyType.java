package com.beehive.domain.honey;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name = "honeyType", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})

public class HoneyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotNull
    private Double price;
    
    @NotBlank
	@Size(max = 100)
	private String description;
    
    public HoneyType() {}

    public HoneyType(String name, Double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
		this.id = id;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}

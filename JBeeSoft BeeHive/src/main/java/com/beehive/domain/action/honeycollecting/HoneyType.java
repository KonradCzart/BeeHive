package com.beehive.domain.action.honeycollecting;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

@Entity
public class HoneyType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NaturalId
	private String name;
	
	@NotBlank
	@Size(max = 100)
	private String description;
	
	public HoneyType(String name, String description) {
		this.name = name;
		this.description = description;
	}

}

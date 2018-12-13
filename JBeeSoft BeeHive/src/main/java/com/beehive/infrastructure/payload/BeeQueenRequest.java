package com.beehive.infrastructure.payload;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BeeQueenRequest {
	
	@NotNull
	private Long race_id;
	
	@NotBlank
	private String color;
	
	@NotNull
	private Date age;
	
	@NotNull
	private Boolean isReproducting;
	
	@NotBlank
	private String description;
	
	@NotNull
	private Long hive_id;

	public Long getRace_id() {
		return race_id;
	}

	public void setRace_id(Long race_id) {
		this.race_id = race_id;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
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

	public Long getHive_id() {
		return hive_id;
	}

	public void setHive_id(Long hive_id) {
		this.hive_id = hive_id;
	}

}

package com.beehive.infrastructure.payload;

import java.sql.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BeeQueenRequest {
	
	@NotNull
	private Long raceId;
	
	@NotBlank
	private String color;
	
	@NotNull
	private Date age;
	
	@NotNull
	private Boolean isReproducting;
	
	@NotBlank
	private String description;
	
	@NotNull
	private Long hiveId;

	public Long getRaceId() {
		return raceId;
	}

	public void setRaceId(Long raceId) {
		this.raceId = raceId;
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

	public Long getHiveId() {
		return hiveId;
	}

	public void setHiveId(Long hiveId) {
		this.hiveId = hiveId;
	}

}

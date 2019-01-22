package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ApiaryRequest {
	
	@NotBlank
	@Size(max = 40)
	private String name;
	
	@NotBlank
	@Size(max = 40)
	private String country;
	
	@NotBlank
	@Size(max = 40)
	private String city;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	
}

package com.beehive.infrastructure.payload;

import com.beehive.domain.location.Location;

public class ApiaryDTO {
	
	private Long id;
	private String name;
	private String country;
	private String city;
	
	public ApiaryDTO() {
		
	}
	
	public ApiaryDTO(ApiaryDTOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.country = builder.country;
		this.city = builder.city;
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
	
	public static ApiaryDTOBuilder builder() {
    	return new ApiaryDTOBuilder();
    }
	
	public static class ApiaryDTOBuilder{
		
		private Long id;
		private String name;
		private String country;
		private String city;
		
		public ApiaryDTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public ApiaryDTOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ApiaryDTOBuilder withLocation(Location location) {
			this.country = location.getCountry();
			this.city = location.getCity();
			return this;
		}
		
        public ApiaryDTO build() {
			return new ApiaryDTO(this);
		}
	}
	
}

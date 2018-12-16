package com.beehive.infrastructure.payload;

import com.beehive.domain.location.Location;

public class ApiaryINFO {
	
	private Long id;
	private String name;
	private String country;
	private String city;
	private long hiveNumber;
	
	public ApiaryINFO() {
		
	}
	
	public ApiaryINFO(ApiaryINFOBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.country = builder.country;
		this.city = builder.city;
		this.hiveNumber = builder.hiveNumber;
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
	
	public long getHiveNumber() {
		return hiveNumber;
	}

	public void setHiveNumber(int hiveNumber) {
		this.hiveNumber = hiveNumber;
	}

	public static ApiaryINFOBuilder builder() {
    	return new ApiaryINFOBuilder();
    }
	
	public static class ApiaryINFOBuilder{
		
		private Long id;
		private String name;
		private String country;
		private String city;
		private long hiveNumber;
		
		public ApiaryINFOBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public ApiaryINFOBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ApiaryINFOBuilder withHiveNumber(long hiveNumber) {
			this.hiveNumber = hiveNumber;
			return this;
		}
		public ApiaryINFOBuilder withLocation(Location location) {
			this.country = location.getCountry();
			this.city = location.getCity();
			return this;
		}
		
        public ApiaryINFO build() {
			return new ApiaryINFO(this);
		}
	}
}

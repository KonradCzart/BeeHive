package com.beehive.infrastructure.payload;

import java.sql.Date;

public class BeeQueenDTO {
	
	private Long id;
	private String color;
	private Boolean isReproducting;
	private String description;
	private String raceName;
	private Date age;
	
	public BeeQueenDTO() {
		
	}
	
	public BeeQueenDTO(BeeQueenDTOBuilder builder) {
		this.id = builder.id;
		this.color = builder.color;
		this.isReproducting = builder.isReproducting;
		this.description = builder.description;
		this.raceName = builder.raceName;
		this.age = builder.age;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
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
	public String getRaceName() {
		return raceName;
	}
	
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	
	public Date getAge() {
		return age;
	}

	public void setAge(Date age) {
		this.age = age;
	}

	public static BeeQueenDTOBuilder builder() {
		return new BeeQueenDTOBuilder();
	}
	
	public static class BeeQueenDTOBuilder {
		
		private Long id;
		private String color;
		private Boolean isReproducting;
		private String description;
		private String raceName;
		private Date age;
		
		public BeeQueenDTOBuilder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public BeeQueenDTOBuilder withColor(String color) {
			this.color = color;
			return this;
		}
		
		public BeeQueenDTOBuilder withIsReproducting(Boolean isReproducting) {
			this.isReproducting = isReproducting;
			return this;
		}
		
		public BeeQueenDTOBuilder withAge(Date age) {
			this.age = age;
			return this;
		}
		
		public BeeQueenDTOBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
		
		public BeeQueenDTOBuilder withRaceName(String raceName) {
			this.raceName = raceName;
			return this;
		}
		
		public BeeQueenDTO build() {
			return new BeeQueenDTO(this);
		}
		
	}
	
}

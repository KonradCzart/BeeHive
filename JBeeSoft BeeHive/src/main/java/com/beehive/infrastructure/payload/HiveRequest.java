package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HiveRequest {
	
	@NotNull
	private Long apiary_id;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Long hiveType_id;
	
	@NotNull
	private int boxNumber;

	public Long getApiary_id() {
		return apiary_id;
	}

	public void setApiary_id(Long apiary_id) {
		this.apiary_id = apiary_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHiveType_id() {
		return hiveType_id;
	}

	public void setHiveType_id(Long hiveType_id) {
		this.hiveType_id = hiveType_id;
	}

	public int getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}
	
	
	
	
	
	
}

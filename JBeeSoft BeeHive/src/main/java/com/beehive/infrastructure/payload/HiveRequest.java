package com.beehive.infrastructure.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class HiveRequest {
	
	@NotNull
	private Long apiaryId;
	
	@NotBlank
	private String name;
	
	@NotNull
	private Long hiveTypeId;
	
	@NotNull
	private Integer boxNumber;

	public Long getApiaryId() {
		return apiaryId;
	}

	public void setApiaryId(Long apiaryId) {
		this.apiaryId = apiaryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getHiveTypeId() {
		return hiveTypeId;
	}

	public void setHiveTypeId(Long hiveTypeId) {
		this.hiveTypeId = hiveTypeId;
	}

	public Integer getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Integer boxNumber) {
		this.boxNumber = boxNumber;
	}	
	
}

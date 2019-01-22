package com.beehive.infrastructure.payload;

import java.util.List;


public class ApiaryDTO {
	
	private ApiaryINFO apiaryINFO;
	private List<HiveDTO> hives;
	
	public ApiaryDTO() {
		
	}
	
	public ApiaryDTO(ApiaryINFO apiaryINFO, List<HiveDTO> hives) {
		this.apiaryINFO = apiaryINFO;
		this.hives = hives;
	}

	public ApiaryINFO getApiaryINFO() {
		return apiaryINFO;
	}

	public void setApiaryINFO(ApiaryINFO apiaryINFO) {
		this.apiaryINFO = apiaryINFO;
	}

	public List<HiveDTO> getHives() {
		return hives;
	}

	public void setHives(List<HiveDTO> hives) {
		this.hives = hives;
	}
	
	
	
}

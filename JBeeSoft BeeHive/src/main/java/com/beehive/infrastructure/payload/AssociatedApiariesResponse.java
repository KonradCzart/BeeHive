package com.beehive.infrastructure.payload;

import java.util.List;

public class AssociatedApiariesResponse {
	
	List<ApiaryINFO> ownedApiaries;
	List<ApiaryINFO> otherApiaries;
	
	public AssociatedApiariesResponse(List<ApiaryINFO> ownedApiaries, List<ApiaryINFO> otherApiaries) {
		this.ownedApiaries = ownedApiaries;
		this.otherApiaries = otherApiaries;
	}
	
	public List<ApiaryINFO> getOwnedApiaries() {
		return ownedApiaries;
	}
	
	public void setOwnedApiaries(List<ApiaryINFO> ownedApiaries) {
		this.ownedApiaries = ownedApiaries;
	}
	
	public List<ApiaryINFO> getOtherApiaries() {
		return otherApiaries;
	}
	
	public void setOtherApiaries(List<ApiaryINFO> otherApiaries) {
		this.otherApiaries = otherApiaries;
	}

}

package com.beehive.infrastructure.payload;

import java.util.List;

public class AssociatedApiariesResponse {
	
	List<ApiaryINFO> ownedApiaries;
	List<ApiaryINFO> otherAppiaries;
	
	public AssociatedApiariesResponse(List<ApiaryINFO> ownedApiaries, List<ApiaryINFO> otherApiaries) {
		this.ownedApiaries = ownedApiaries;
		this.otherAppiaries = otherApiaries;
	}
	
	public List<ApiaryINFO> getOwnedApiaries() {
		return ownedApiaries;
	}
	
	public void setOwnedApiaries(List<ApiaryINFO> ownedApiaries) {
		this.ownedApiaries = ownedApiaries;
	}
	
	public List<ApiaryINFO> getOtherAppiaries() {
		return otherAppiaries;
	}
	
	public void setOtherAppiaries(List<ApiaryINFO> otherAppiaries) {
		this.otherAppiaries = otherAppiaries;
	}

}

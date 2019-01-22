package com.beehive.infrastructure.payload;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

public class ActionRequest {

	@NotEmpty
	private Set<Long> affectedHives;
	
	public Set<Long> getAffectedHives() {
		return affectedHives;
	}
	
	public void setAffectedHives(Set<Long> affectedHives) {
		this.affectedHives = affectedHives;
	}
	
}

package com.beehive.infrastructure.payload;

import java.util.List;


public class GrantPrivilegesRequest {
	
	private Long targetUser;
	private List<String> privileges;
    private Long affectedApiaryId;

	public Long getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(Long targetUser) {
		this.targetUser = targetUser;
	}
    
	public List<String> getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	
	public Long getAffectedApiaryId() {
		return affectedApiaryId;
	}
	
	public void setAffectedApiaryId(Long affectedApiaryId) {
		this.affectedApiaryId = affectedApiaryId;
	}
    
}

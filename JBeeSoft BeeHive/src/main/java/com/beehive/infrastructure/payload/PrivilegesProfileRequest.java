package com.beehive.infrastructure.payload;

import java.util.Set;

import com.beehive.domain.privileges.Privilege;

public class PrivilegesProfileRequest {

    private Set<Privilege> privileges;
    private Long affectedApiaryId;
    private Long targetUserId;
    
    public PrivilegesProfileRequest(Set<Privilege> privileges, Long affectedApiaryId, Long targetUserId) {
		this.privileges = privileges;
		this.affectedApiaryId = affectedApiaryId;
		this.targetUserId = targetUserId;
	}
    
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

	public Long getAffectedApiaryId() {
		return affectedApiaryId;
	}

	public void setAffectedApiaryId(Long affectedApiaryId) {
		this.affectedApiaryId = affectedApiaryId;
	}

	public Long getTargetUserId() {
		return targetUserId;
	}

	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
    
}

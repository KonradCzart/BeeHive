package com.beehive.infrastructure.payload;

import java.util.Set;

import com.beehive.domain.privileges.Privilege;

public class ContributorDTO {
	
	private Long userId;
	private String username;
	private String email;
	private Set<Privilege> privileges;
	
	public ContributorDTO(Builder builder) {
		userId = builder.userId;
		username = builder.username;
		email = builder.email;
		privileges = builder.privileges;
	}
	
	public Long getUserId() {
		return userId;
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<Privilege> getPrivileges() {
		return privileges;
	}
	
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}	
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Long userId;
		private String username;
		private String email;
		private Set<Privilege> privileges;
		
		public Builder withUserId(Long userId) {
			this.userId = userId;
			return this;
		}
		
		public Builder withUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder withPrivileges(Set<Privilege> privileges) {
			this.privileges = privileges;
			return this;
		}
		
		public ContributorDTO build() {
			return new ContributorDTO(this);
		}
		
	}
	
}

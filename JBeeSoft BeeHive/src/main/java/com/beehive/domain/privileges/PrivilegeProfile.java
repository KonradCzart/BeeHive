package com.beehive.domain.privileges;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.user.User;

import java.util.Collections;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "privilege_profile")
public class PrivilegeProfile {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToMany
    @JoinTable(name = "privilegeProfile_privilege")
    private Set<Privilege> privileges;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "affectedApiaryId")
    private Apiary affectedApiary;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User targetUser;

    public PrivilegeProfile() {}

	public PrivilegeProfile(Builder builder) {
    	this.id = builder.id;
    	this.affectedApiary = builder.affectedApiary;
    	this.privileges = builder.privileges;
    	this.targetUser = builder.targetUser;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Apiary getAffectedApiary() {
		return affectedApiary;
	}

	public void setAffectedApiary(Apiary affectedApiary) {
		this.affectedApiary = affectedApiary;
	}
	
	public Set<Privilege> getPrivileges() {
    	return Collections.unmodifiableSet(privileges);
    }

	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}
	
	public User getTargetUser() {
		return targetUser;
	}

	public void setTargetUser(User targetUser) {
		this.targetUser = targetUser;
	}
	
	public boolean isApiaryOwner() {
		return privileges.contains(Privilege.OWNER_PRIVILEGE);
	}
	
	public static Builder builder() {
		return new Builder();
	}
	
	public static class Builder {
		
		private Long id;
		private Set<Privilege> privileges;
		private Apiary affectedApiary;
		private User targetUser;
		
		public Builder withId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder withPrivileges(Set<Privilege> privileges) {
			this.privileges = privileges;
			return this;
		}
		
		public Builder withAffectedApiary(Apiary affedtedAppiary) {
			this.affectedApiary = affedtedAppiary;
			return this;
		}
		
		public Builder withTargetUser(User targetUser) {
			this.targetUser = targetUser;
			return this;
		}

		public PrivilegeProfile build() {
			return new PrivilegeProfile(this);
		}
		
	}
	
}

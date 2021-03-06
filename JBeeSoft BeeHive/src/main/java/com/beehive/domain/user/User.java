package com.beehive.domain.user;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.dateaudit.DateAudit;
import com.beehive.domain.privileges.PrivilegeProfile;
import com.beehive.domain.userrole.Role;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User extends DateAudit {
 
	private static final long serialVersionUID = 4050814255685071574L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NaturalId
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    @OneToMany(mappedBy = "targetUser")
    @MapKey(name = "affectedApiary")
    private Map<Apiary, PrivilegeProfile> privilegeProfileForApiaryMap;

    public User() {

    }

    public User(UserBuilder builder) {
    	this.id = builder.id;
        this.name = builder.name;
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.privilegeProfileForApiaryMap = builder.privilegeProfileForApiaryMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public Map<Apiary, PrivilegeProfile> getPrivilegeProfileForApiaryMap() {
		return privilegeProfileForApiaryMap;
	}

	public void setPrivilegeProfileForApiaryMap(Map<Apiary, PrivilegeProfile> privilegeProfileForApiaryMap) {
		this.privilegeProfileForApiaryMap = privilegeProfileForApiaryMap;
	}

	public Optional<PrivilegeProfile> getPrivilegeProfileForApiary(Apiary apiary) {
		return Optional.ofNullable(privilegeProfileForApiaryMap.get(apiary));
	}

	public void updatePrivilegeProfileForApiary(PrivilegeProfile profile, Apiary apiary) {
		privilegeProfileForApiaryMap.put(apiary, profile);
	}
	
	public void removePrivilegeProfileForApiary(Apiary apiary) {
		privilegeProfileForApiaryMap.remove(apiary);
	}
	
	public Collection<PrivilegeProfile> getAllPrivilegeProfiles() {
		return privilegeProfileForApiaryMap.values();
	}
    
    public static UserBuilder builder() {
    	return new UserBuilder();
    }
    
    public static class UserBuilder {
    	
    	private Long id;
        private String name;
        private String username;
        private String email;
        private String password;
        private Map<Apiary, PrivilegeProfile> privilegeProfileForApiaryMap;
        
        public UserBuilder withId(Long id) {
			this.id = id;
			return this;
		}
        
        public UserBuilder withName(String name) {
			this.name = name;
			return this;
		}
        
        public UserBuilder withUsername(String username) {
			this.username = username;
			return this;
		}
        
        public UserBuilder withEmail(String email) {
			this.email = email;
			return this;
		}
        
        public UserBuilder withPassword(String password) {
			this.password = password;
			return this;
		}
        
        public UserBuilder withPrivilegeProfileForApiaryMap(Map<Apiary, PrivilegeProfile> privilegeProfileForApiaryMap) {
			this.privilegeProfileForApiaryMap = privilegeProfileForApiaryMap;
			return this;
		}
        
        public User build() {
			return new User(this);
		}
    	
    }
    
}

package com.beehive.domain.userrole;

import org.hibernate.annotations.NaturalId;

import com.beehive.domain.privilege.Privilege;

import java.util.Collections;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NaturalId
    @Column(length = 60)
    private RoleName name;
    
    @ManyToMany
    @JoinTable(name = "userrole_privilege")
    private Set<Privilege> privileges;

    public Role() {

    }

    public Role(RoleName name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
    
    public Set<Privilege> getPrivileges() {
    	return Collections.unmodifiableSet(privileges);
    }
}

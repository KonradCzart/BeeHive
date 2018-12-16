package com.beehive.domain.apiary;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.location.Location;
import com.beehive.domain.user.User;


@Entity
@Table(name = "apiary")
public class Apiary {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    
    @OneToMany(mappedBy = "apiary")
    private Set<Hive> hives;
    //!!! both ends of relation have to be updated !!!
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;
    
    public Apiary() {
    	
    }
    
    public Apiary(String name, User owner, Location location ) {
    	this.hives = new HashSet<>();
    	this.name = name;
    	this.owner = owner;
    	this.location = location;    	
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Set<Hive> getHives() {
		return hives;
	}

	public void setHives(Set<Hive> hives) {
		this.hives = hives;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	public boolean addHive(Hive hive) {
		return hives.add(hive);
	}
	
	public boolean removeHive(Hive hive) {
		return hives.remove(hive);
	}
}

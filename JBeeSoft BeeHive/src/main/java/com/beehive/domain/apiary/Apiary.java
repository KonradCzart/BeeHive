package com.beehive.domain.apiary;

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

import org.hibernate.annotations.NaturalId;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.location.Location;
import com.beehive.domain.user.User;


@Entity
@Table(name = "apiary")
public class Apiary {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NaturalId
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
    
}

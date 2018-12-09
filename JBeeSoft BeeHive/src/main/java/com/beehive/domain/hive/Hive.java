package com.beehive.domain.hive;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.beehive.domain.apiary.Apiary;

@Entity
public class Hive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @ManyToOne
    @JoinColumn(name = "apiary_id")
    private Apiary apiary;
    //!!! both ends of relation have to be updated !!!

    @NotBlank
    @Size(max = 40)
    private String name;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id")
    private HiveType hiveType;

    public Hive() {
        
    }
    
    public Apiary getApiary() {
        return apiary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public HiveType getType() {
    	return hiveType;
    }
}

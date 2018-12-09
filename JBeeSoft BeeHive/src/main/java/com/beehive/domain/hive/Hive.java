package com.beehive.domain.hive;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
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

    public Hive() {
        
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

    public Apiary getApiary() {
        return apiary;
    }
}

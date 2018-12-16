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
import com.beehive.domain.bee.queen.BeeQueen;

@Entity
public class Hive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
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
    
    @NotNull
    private int boxNumber;
    
    @OneToOne
    @JoinColumn(name = "queen_id")
    private BeeQueen beeQueen;

    
    public Hive() {
        
    }
    
    public Hive(String name, Apiary apiary, HiveType hiveType, int boxNumber) {
    	this.name = name;
    	this.apiary = apiary;
    	this.hiveType = hiveType;
    	this.boxNumber = boxNumber;
    	this.beeQueen = null;
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

	public int getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(int boxNumber) {
		this.boxNumber = boxNumber;
	}

	public BeeQueen getBeeQueen() {
		return beeQueen;
	}

	public void setBeeQueen(BeeQueen beeQueen) {
		this.beeQueen = beeQueen;
	}

	public void setApiary(Apiary apiary) {
		this.apiary = apiary;
	}
    
}

package com.beehive.domain.hive;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    private Integer boxNumber;
    
    @OneToOne
    @JoinColumn(name = "queen_id")
    private BeeQueen beeQueen;
    
    @NotNull
    private Boolean isExist;

    
    public Hive() {
        
    }
    
    public Hive(String name, Apiary apiary, HiveType hiveType, Integer boxNumber) {
    	this.name = name;
    	this.apiary = apiary;
    	this.hiveType = hiveType;
    	this.boxNumber = boxNumber;
    	this.beeQueen = null;
    	this.isExist = true;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    
    public Apiary getApiary() {
        return apiary;
    }
    
    public void setApiary(Apiary apiary) {
		this.apiary = apiary;
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

	public Integer getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(Integer boxNumber) {
		this.boxNumber = boxNumber;
	}

	public BeeQueen getBeeQueen() {
		return beeQueen;
	}

	public void setBeeQueen(BeeQueen beeQueen) {
		this.beeQueen = beeQueen;
	}

	public HiveType getHiveType() {
		return hiveType;
	}

	public void setHiveType(HiveType hiveType) {
		this.hiveType = hiveType;
	}

	public Boolean getIsExist() {
		return isExist;
	}

	public void setIsExist(Boolean isExist) {
		this.isExist = isExist;
	}
}

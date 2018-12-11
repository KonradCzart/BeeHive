package com.beehive.domain.bee.queen;

import com.beehive.domain.bee.race.BeeRace;
import com.beehive.domain.dateaudit.DateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "beeQueen")

public class BeeQueen extends DateAudit {
   
	private static final long serialVersionUID = 545295226829677088L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "race_id")
    private BeeRace beeRace;

    @NotBlank
    @Size(max = 40)
    private String color;

    @NotNull
    private Integer age;

    @NotNull
    private Boolean isReproducting;

    @Size(max = 100)
    private String description;

    public BeeQueen(BeeQueenBuilder builder) {
        this.id = builder.id;
        this.beeRace = builder.beeRace;
        this.color = builder.color;
        this.age = builder.age;
        this.isReproducting = builder.isReproducting;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
		this.id = id;
	}
    
    public BeeRace getBeeRace() {
		return beeRace;
	}

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsReproducting() {
        return isReproducting;
    }

    public void setIsReproducting(Boolean isReproducting) {
        this.isReproducting = isReproducting;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    static public class BeeQueenBuilder {
    	
        private Long id;
        private BeeRace beeRace;
        private String color;
        private Integer age;
        private Boolean isReproducting;
        private String description;
        
        public BeeQueenBuilder withId(Long id) {
			this.id = id;
			return this;
		}
        
        public BeeQueenBuilder withBeeRace(BeeRace beeRace) {
			this.beeRace = beeRace;
			return this;
		}
        
        public BeeQueenBuilder withColor(String color) {
			this.color = color;
			return this;
		}
        
        public BeeQueenBuilder withAge(Integer age) {
			this.age = age;
			return this;
		}
        
        public BeeQueenBuilder withIsReproducing(Boolean isReproducing) {
			this.isReproducting = isReproducing;
			return this;
		}
        
        public BeeQueenBuilder withDescription(String description) {
			this.description = description;
			return this;
		}
        
        public BeeQueen build() {
			return new BeeQueen(this);
		}
    }
    
}

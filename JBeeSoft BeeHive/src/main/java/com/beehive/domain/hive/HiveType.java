package com.beehive.domain.hive;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "hive_type", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        })
})
public class HiveType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    private String description;
    
    @NotNull
    private int frameCapacity;
    
    public HiveType() {
    	
    }
    
    public HiveType(String name, String description, int frameCapacity) {
        this.name = name;
        this.description = description;
        this.frameCapacity = frameCapacity;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String  getDescription() {
        return description;
    }
    
    public int getFrameCapacity() {
    	return frameCapacity;
    }
    
}

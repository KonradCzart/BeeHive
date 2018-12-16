package com.beehive.domain.privileges;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "privileges", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "name"
        }),
        @UniqueConstraint(columnNames = {
                "readableName"
        })
})
public class Privilege {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotBlank
	@NaturalId
	private String name;
	
	@NotBlank
	private String readableName;
	
	@NotBlank
	private String description;
	
	public static final Privilege OWNER_PRIVILEGE = new Privilege("OWNER_PRIVILEGE");
	public static final Privilege HIVE_EDITING = new Privilege("HIVE_EDITING");
	public static final Privilege APIARY_EDITING = new Privilege("APIARY_EDITING");
	public static final Privilege HIVE_STATS_READING = new Privilege("HIVE_STATS_READING");
	public static final Privilege APIARY_STATS_READING = new Privilege("APIARY_STATS_READING");
	
	public static Set<Privilege> getAllAvailablePrivileges() {
		return Set.of(OWNER_PRIVILEGE, HIVE_EDITING, APIARY_EDITING, HIVE_STATS_READING, APIARY_STATS_READING);
	}
	
	public Privilege(Long id, String name, String readableName, String description) {
		this.id = id;
		this.name = name;
		this.readableName = readableName;
		this.description = description;
	}
	
	public Privilege(String name) {
		this.name = name;
	}
	
	public Privilege() {}
	
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
	
	public String getReadableName() {
		return readableName;
	}

	public void setReadableName(String readableName) {
		this.readableName = readableName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object object) {
		if(object == this) {
			return true;
		}
		
		if(!(object instanceof Privilege)) {
			return false;
		}
		
		Privilege privilege = (Privilege) object;
		
		return privilege.name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}

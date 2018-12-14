package com.beehive.domain.user;

import com.beehive.domain.dateaudit.DateAudit;
import com.beehive.domain.userrole.Role;

import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notification", uniqueConstraints = {
})

public class Notification extends DateAudit {
 
	private static final long serialVersionUID = 4050814255685071574L;

    @NotBlank
    private Long userId;

    @NotBlank
    private Date date;

    @NotBlank
    @Size(max = 40)
    private String name;

    @Size(max = 1000)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "users",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))

    public Notificarion() {

    }

    public Notification(Long userId, Date data, String description) {
    	this.userId =userId;
        this.date = date;
        this.description = description;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) { this.userId = userId;  }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description;   }

    public Set<Role> getRoles() {
        return roles;
    }

}

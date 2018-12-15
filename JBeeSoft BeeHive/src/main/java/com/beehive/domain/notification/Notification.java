package com.beehive.domain.notification;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.beehive.domain.user.User;


@Entity
@Table(name = "notification")
public class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @NotBlank
    @Size(max = 40)
    private String title;
    
    @NotBlank
    private String description;
    
    @NotNull
    private Date date;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "notification_user",
            joinColumns = @JoinColumn(name = "notification_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();
    
    public Notification() {
    	
    }
    
    public Notification(NotificationBuilder builder) {
    	this.title = builder.title;
    	this.description = builder.description;
    	this.date = builder.date;
    	this.users = builder.users;
    	
    }
    
    public Notification(String title, String description, Date date) {
    	this.title = title;
    	this.description = description;
    	this.date = date;
    }
    
	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
    public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
    	users.add(user);
    }
	
	public static NotificationBuilder builder() {
		return new NotificationBuilder();
	}
	
	public static class NotificationBuilder{
		
	    private String title;
	    private String description;
	    private Date date;
	    private Set<User> users;
	    
	    public NotificationBuilder() {
	    	users = new HashSet<>();
	    }
	    
	    public NotificationBuilder withTitle(String title) {
	    	this.title = title;
	    	return this;
	    }
	    
	    public NotificationBuilder withDescription(String description) {
	    	this.description = description;
	    	return this;
	    }
	    
	    public NotificationBuilder withDate(Date date) {
	    	this.date = date;
	    	return this;
	    }
	    
	    public NotificationBuilder withUsers(Set<User> users) {
	    	this.users = users;
	    	return this;
	    }
	    
	    public NotificationBuilder withUser(User user) {
	    	this.users.add(user);
	    	return this;
	    }
	    
	    public Notification build() {
	    	return new Notification(this);
	    }
	}
}

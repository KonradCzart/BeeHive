package com.beehive.infrastructure.payload;

import java.util.Date;
import java.util.List;


public class NotificationDTO {
	
	private Long id;
    private String title;
    private String description;
    private Date date;
    private Boolean isRealize;
    private UserDTO author;
    private List<UserDTO> users;
    
    public NotificationDTO() {
    	
    }
    
    public NotificationDTO(NotificationDTOBuilder builder) {
    	this.title = builder.title;
    	this.description = builder.description;
    	this.date = builder.date;
    	this.isRealize = builder.isRealize;
    	this.id = builder.id;
    	this.author = builder.author;
    	this.users = builder.users;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Boolean getIsRealize() {
		return isRealize;
	}

	public void setIsRealize(Boolean isRealize) {
		this.isRealize = isRealize;
	}

	public UserDTO getAuthor() {
		return author;
	}

	public void setAuthor(UserDTO author) {
		this.author = author;
	}

	public List<UserDTO> getUsers() {
		return users;
	}

	public void setUsers(List<UserDTO> users) {
		this.users = users;
	}

	public static NotificationDTOBuilder builder() {
		return new NotificationDTOBuilder();
	}
	
	public static class NotificationDTOBuilder{
		private Long id;
	    private String title;
	    private String description;
	    private Date date;
	    private Boolean isRealize;
	    private UserDTO author;
	    private List<UserDTO> users;
	    
	    public NotificationDTOBuilder withId(Long id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withTitle(String title) {
	    	this.title = title;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withDescription(String description) {
	    	this.description = description;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withDate(Date date) {
	    	this.date = date;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withIsRealize(Boolean isRealize) {
	    	this.isRealize = isRealize;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withAuthor(UserDTO author) {
	    	this.author = author;
	    	return this;
	    }
	    
	    public NotificationDTOBuilder withUsers(List<UserDTO> users) {
	    	this.users = users;
	    	return this;
	    }
	    public NotificationDTO build() {
	    	return new NotificationDTO(this);
	    }
	}    

}

package com.beehive.infrastructure.payload;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class NotificationRequest {
	
	@NotBlank
    private String title;
	
	@NotBlank
    private String description;
	
	@NotNull
    private Date date;
	
	@NotNull
	private Boolean isRealize;
	
	@NotNull
    private List<Long> usersId;

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

	public List<Long> getUsersId() {
		return usersId;
	}

	public void setUsers(List<Long> usersId) {
		this.usersId = usersId;
	}

	public Boolean getIsRealize() {
		return isRealize;
	}

	public void setIsRealize(Boolean isRealize) {
		this.isRealize = isRealize;
	}	
}

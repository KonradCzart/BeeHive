package com.beehive.infrastructure.payload;


import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NotificationModifyRequest {

	@NotNull
	private Long id;
	
	@NotBlank
    private String title;
	
	@NotBlank
    private String description;
	
	@NotNull
	private Boolean isRealize;
	
	@NotNull
    private Date date;

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

	public Boolean getIsRealize() {
		return isRealize;
	}

	public void setIsRealize(Boolean isRealize) {
		this.isRealize = isRealize;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

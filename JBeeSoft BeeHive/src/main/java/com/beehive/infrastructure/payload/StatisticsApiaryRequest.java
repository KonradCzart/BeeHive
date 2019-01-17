package com.beehive.infrastructure.payload;

import java.util.Date;

import javax.validation.constraints.NotNull;

public class StatisticsApiaryRequest {
	
	@NotNull
    private Date  beginningDate;
	
	@NotNull Date endDate;

	public Date getBeginningDate() {
		return beginningDate;
	}

	public void setBeginningDate(Date beginningDate) {
		this.beginningDate = beginningDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}

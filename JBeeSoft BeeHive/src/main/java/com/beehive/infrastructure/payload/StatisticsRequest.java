package com.beehive.infrastructure.payload;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class StatisticsRequest {
	
	
	@NotNull
    private Date  beginningDate;
	
	@NotNull Date endDate;
	
	@NotNull
    private Set<Long> hiveId;

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

	public Set<Long> getHiveId() {
		return hiveId;
	}

	public void setHiveId(Set<Long> hiveId) {
		this.hiveId = hiveId;
	}	

}

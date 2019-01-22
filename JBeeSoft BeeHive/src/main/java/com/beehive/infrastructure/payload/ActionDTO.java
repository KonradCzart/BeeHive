package com.beehive.infrastructure.payload;

import java.util.Date;
import java.util.Set;


public abstract class ActionDTO {
	
	private Long id;
    private Set<HiveDTO> affectedHives;
    private Date date;
    private UserDTO performer;
    private String actionName;
    
    public ActionDTO() {
		
	}
    
    ActionDTO(Builder<?> builder) {
		id = builder.id;
		affectedHives = builder.affectedHives;
		date = builder.date;
		performer = builder.performer;
		actionName = builder.actionName;
	}
    
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Set<HiveDTO> getAffectedHives() {
		return affectedHives;
	}
	
	public void setAffectedHives(Set<HiveDTO> affectedHives) {
		this.affectedHives = affectedHives;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public UserDTO getPerformer() {
		return performer;
	}
	
	public void setPerformer(UserDTO performer) {
		this.performer = performer;
	}
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
    
    public static abstract class Builder<T extends Builder<T>> {
    	
    	private Long id;
        private Set<HiveDTO> affectedHives;
        private Date date;
        private UserDTO performer;
        private String actionName;
        
        protected abstract T self();
        
        public T withId(Long id) {
			this.id = id;
			return self();
		}
        
        public T withAffectedHives(Set<HiveDTO> affectedHives) {
			this.affectedHives = affectedHives;
			return self();
		}
        
        public T withDate(Date date) {
			this.date = date;
			return self();
		}
        
        public T withPerformer(UserDTO performer) {
			this.performer = performer;
			return self();
		}
        
        public T withActionName(String actionName) {
			this.actionName = actionName;
			return self();
		}
        
        public abstract ActionDTO build();
        
    }
    
}

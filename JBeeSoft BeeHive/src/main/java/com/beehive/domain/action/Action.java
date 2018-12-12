package com.beehive.domain.action;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "action")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Action {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @NotNull
    @ManyToMany
    private Set<Hive> affectedHives;

    @NotNull
    private Date date;
    
    @NotNull
    @ManyToOne
    @JoinColumn (name = "user_id")
    private User performer;
    

    public Action(Builder<?> builder) {
        this.id = builder.id;
        this.affectedHives = builder.affectedHives;
        this.date = builder.date;
        this.performer = builder.performer;
    }
    
    
    public static abstract class Builder<T extends Builder<T>> {
    	
    	private Long id;
        private Set<Hive> affectedHives;
        private Date date;
        private User performer;
        
        protected abstract T self();
        
        public T withId(Long id) {
			this.id = id;
			return self();
		}
        
        public T withAffectedHives(Set<Hive> affectedHives) {
        	this.affectedHives = affectedHives;
        	return self();
        }
        
        public T withDate(Date date) {
			this.date = date;
			return self();
		}
        
        
        public T withPerformer(User performer) {
			this.performer = performer;
			return self();
		}
        
        public abstract Action build();
        
    }
 
}
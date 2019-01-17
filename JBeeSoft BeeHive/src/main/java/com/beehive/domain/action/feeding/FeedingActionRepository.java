package com.beehive.domain.action.feeding;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.beehive.domain.hive.Hive;
import com.beehive.domain.user.User;

@Repository
public interface FeedingActionRepository extends JpaRepository<FeedingAction, Long>{
	
	List<FeedingAction> findByAffectedHivesInAndDateBetween(Set<Hive> affectedHives, Date start, Date end);
	
	Long countByAffectedHivesInAndDateBetweenAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer);
}

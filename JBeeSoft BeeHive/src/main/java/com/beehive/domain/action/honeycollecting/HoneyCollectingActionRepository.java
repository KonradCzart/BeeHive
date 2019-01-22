package com.beehive.domain.action.honeycollecting;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.user.User;


public interface HoneyCollectingActionRepository extends JpaRepository<HoneyCollectingAction, Long>{
	
	List<HoneyCollectingAction> findByAffectedHivesInAndDateBetween(Set<Hive> affectedHives, Date start, Date end);
	
	Long countByAffectedHivesInAndDateBetweenAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer);
}

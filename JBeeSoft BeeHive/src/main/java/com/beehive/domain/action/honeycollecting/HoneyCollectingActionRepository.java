package com.beehive.domain.action.honeycollecting;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.beehive.domain.hive.Hive;


public interface HoneyCollectingActionRepository extends JpaRepository<HoneyCollectingAction, Long>{
	
	List<HoneyCollectingAction> findByAffectedHivesInAndDateBetween(Set<Hive> affectedHives, Date start, Date end);
}

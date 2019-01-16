package com.beehive.domain.action.feeding;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.beehive.domain.hive.Hive;

@Repository
public interface FeedingActionRepository extends JpaRepository<FeedingAction, Long>{
	
	List<FeedingAction> findByAffectedHivesInAndDateBetween(Set<Hive> affectedHives, Date start, Date end);
}

package com.beehive.domain.action.inspection;

import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.user.User;

public interface InspectionRepository extends JpaRepository<InspectionAction, Long>{	
	
	Long countByAffectedHivesInAndDateBetweenAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer);
}


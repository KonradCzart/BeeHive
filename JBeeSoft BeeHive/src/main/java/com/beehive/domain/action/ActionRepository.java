package com.beehive.domain.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beehive.domain.hive.Hive;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {
	  List<Action> findDistinctByAffectedHivesIn(Set<Hive> affectedHives);
}


package com.beehive.domain.bee.race;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BeeRaceRepository extends JpaRepository<BeeRace, Long> {
	
    Optional<BeeRace> findById(Long id);
    
    Optional<BeeRace> findByName(String name);
}
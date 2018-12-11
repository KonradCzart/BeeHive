package com.beehive.domain.bee.race;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeeRaceRepository extends JpaRepository<BeeRace, Long> {
    Optional<BeeRace> findById(Long id);
}
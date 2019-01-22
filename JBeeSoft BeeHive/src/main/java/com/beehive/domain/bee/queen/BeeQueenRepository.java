package com.beehive.domain.bee.queen;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BeeQueenRepository extends JpaRepository<BeeQueen, Long> {
	
	Optional<BeeQueen> findById(Long id);
    
}
package com.beehive.domain.bee.queen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BeeQueenRepository extends JpaRepository<BeeQueen, Long> {
    
}
package com.beehive.domain.bee.queen;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BeeQueenRepository extends JpaRepository<BeeQueen, Long> {
    
}
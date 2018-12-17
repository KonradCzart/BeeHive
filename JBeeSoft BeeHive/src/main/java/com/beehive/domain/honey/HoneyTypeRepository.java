package com.beehive.domain.honey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HoneyTypeRepository extends JpaRepository<HoneyType, Long>{
	
}

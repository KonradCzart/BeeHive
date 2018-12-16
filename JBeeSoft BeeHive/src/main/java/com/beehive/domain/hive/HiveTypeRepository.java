package com.beehive.domain.hive;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HiveTypeRepository extends JpaRepository<HiveType, Long>{
	
	Optional<HiveType> findById(Long id);
	
	Optional<HiveType> findByName(String name);
}

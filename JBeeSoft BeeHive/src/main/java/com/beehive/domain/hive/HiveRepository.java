package com.beehive.domain.hive;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HiveRepository extends JpaRepository<Hive, Long>{
	
	Optional<Hive> findById(Long id);
	
	List<Hive> findByApiaryId(Long apiary_id);
	
	Set<Hive> findAllByIdIn(Set<Long> ids);
	
}

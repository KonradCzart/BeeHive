package com.beehive.domain.apiary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApiaryRepository extends JpaRepository<Apiary, Long>{
	
	List<Apiary> findByOwnerId(Long owner_id);
	
	List<Apiary> findByLocationId(Long location_id);

}

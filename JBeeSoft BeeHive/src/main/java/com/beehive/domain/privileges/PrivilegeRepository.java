package com.beehive.domain.privileges;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>  {
	public Privilege findByName(String name);
	public List<Privilege> findAllByNameIn(Iterable<String> names);
}

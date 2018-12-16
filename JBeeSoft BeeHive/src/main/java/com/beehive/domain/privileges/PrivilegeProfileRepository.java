package com.beehive.domain.privileges;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.user.User;

public interface PrivilegeProfileRepository extends JpaRepository<PrivilegeProfile, Long>{
	public Optional<PrivilegeProfile> findByTargetUserAndAffectedApiary(User targetUser, Apiary affectedApiary);
}

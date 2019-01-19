package com.beehive.domain.privileges;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.user.User;
import com.beehive.infrastructure.exceptions.AuthorizationException;
import com.beehive.infrastructure.payload.ContributorDTO;

@Service
public class PrivilegeService {
	@Autowired
	private PrivilegeRepository privilegeRepository;
	
	@Autowired
	private PrivilegeProfileRepository privilegeProfileRepository;

	
	private static final String CANNOT_CHANGE_OWN_PRIVILEGES_MSG = "User {0} tried to change their own privileges!";
	private static final String NO_RIGHTS_TO_GRANT_PRIVILEGES = "User {0} is not owner of apiary with id {1} and has no rights to grant permissions!";
	private static final String CANNOT_CREATE_PROFILE_WITH_NO_PRIVILEGE = "Cannot create new PrivilegeProfile with empty privileges set";
	
	public void grantPrivilegesIfAuthorized(User targetUser, Apiary targetApiary, User privilegeGiver, Set<Privilege> privileges) {
		validateRightsForGrantingPrivileges(targetUser, targetApiary, privilegeGiver);		
		grantPrivileges(targetUser, targetApiary, privileges);
	}
	
	private void validateRightsForGrantingPrivileges(User targetUser, Apiary targetApiary, User privilegeGiver) {
		if(targetUser.getId().equals(privilegeGiver.getId())) {
			throw new IllegalArgumentException(
					MessageFormat.format(CANNOT_CHANGE_OWN_PRIVILEGES_MSG, privilegeGiver.getUsername()));
		}
		
		if(!isApiaryOwner(privilegeGiver, targetApiary)) {
			throw new IllegalArgumentException(
					MessageFormat.format(NO_RIGHTS_TO_GRANT_PRIVILEGES, privilegeGiver.getUsername(), targetApiary.getId()));
		}    
	}
	
	private boolean isApiaryOwner(User privilegeGiver, Apiary apiary) {
		return privilegeGiver.getPrivilegeProfileForApiary(apiary)
				.map(PrivilegeProfile::isApiaryOwner)
				.orElse(false);
	}
	
	public void grantPrivileges(User targetUser, Apiary targetApiary, Set<Privilege> privileges) {
		privileges = getPrivilegesFromDatabase(privileges);

		PrivilegeProfile profile = privilegeProfileRepository
				.findByTargetUserAndAffectedApiary(targetUser, targetApiary)
				.orElseGet(PrivilegeProfile::new);
		profile.setAffectedApiary(targetApiary);
		profile.setPrivileges(privileges);
		profile.setTargetUser(targetUser);

		validateIfHasAnyPrivilegeIfNewProfile(profile);	
		privilegeProfileRepository.save(profile);
		targetUser.updatePrivilegeProfileForApiary(profile, targetApiary);
		removeProfileIfNoPrivileges(profile);		
	}
	
	private void validateIfHasAnyPrivilegeIfNewProfile(PrivilegeProfile privilegeProfile) {
		if(privilegeProfile.getId() == null && privilegeProfile.getPrivileges().isEmpty()) {
			throw new IllegalArgumentException(CANNOT_CREATE_PROFILE_WITH_NO_PRIVILEGE);
		}
	}
	
	private void removeProfileIfNoPrivileges(PrivilegeProfile privilegeProfile) {
		if(privilegeProfile.getPrivileges().isEmpty()) {
			privilegeProfileRepository.delete(privilegeProfile);
		}
	}
	
	public void validateHasUserAllRequiredPermissions(User user, Apiary targetApiary, Set<Privilege> requiredPrivileges) {
		if(!hasRequiredPrivileges(user, targetApiary, requiredPrivileges)) {
			throw new AuthorizationException();
		}
	}
	
	public void validateHasUserAnyOfListedPermissions(User user, Apiary targetApiary, Set<Privilege> requiredPrivileges) {
		if(!hasAnyOfListedPrivileges(user, targetApiary, requiredPrivileges)) {
			throw new AuthorizationException();
		}
	}

	private boolean hasRequiredPrivileges(User user, Apiary apiary, Set<Privilege> requiredPrivileges) {
		return user.getPrivilegeProfileForApiary(apiary)
				.map(PrivilegeProfile::getPrivileges)
				.map((ownedPrivileges) -> ownedPrivileges.containsAll(requiredPrivileges))
				.orElse(false);
	}
	
	private boolean hasAnyOfListedPrivileges(User user, Apiary apiary, Set<Privilege> requiredPrivileges) {
		return user.getPrivilegeProfileForApiary(apiary)
				.map(PrivilegeProfile::getPrivileges)
				.map((ownedPrivileges) -> !Collections.disjoint(ownedPrivileges, requiredPrivileges))
				.orElse(false);
	}
	
	private Set<Privilege> getPrivilegesFromDatabase(Set<Privilege> privileges) {
		List<String> privilegeNames = privileges.stream()
				.map(Privilege::getName)
				.collect(Collectors.toList());
		return new HashSet<Privilege>(privilegeRepository.findAllByNameIn(privilegeNames));
	}
	
	public List<PrivilegeProfile> getProfilesForApiary(Apiary apiary) {
		return privilegeProfileRepository.findAllByAffectedApiary(apiary);
	}
	
	public ContributorDTO mapToContributorDTO(PrivilegeProfile profile) {
		return ContributorDTO.builder()
				.withUserId(profile.getTargetUser().getId())
				.withUsername(profile.getTargetUser().getUsername())
				.withEmail(profile.getTargetUser().getEmail())
				.withPrivileges(profile.getPrivileges())
				.build();
	}
	
}

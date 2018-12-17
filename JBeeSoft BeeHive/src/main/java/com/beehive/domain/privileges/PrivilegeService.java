package com.beehive.domain.privileges;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.user.User;

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
				.isApiaryOwner();
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

	public boolean hasRequiredPrivileges(User user, Apiary apiary, Set<Privilege> requiredPrivileges) {
		PrivilegeProfile userProfile = user.getPrivilegeProfileForApiary(apiary);
		return userProfile.getPrivileges().containsAll(requiredPrivileges);
	}
	
	private Set<Privilege> getPrivilegesFromDatabase(Set<Privilege> privileges) {
		List<String> privilegeNames = privileges.stream()
				.map(Privilege::getName)
				.collect(Collectors.toList());
		return new HashSet<Privilege>(privilegeRepository.findAllByNameIn(privilegeNames));
	}
	
	
	
}

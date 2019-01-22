package com.beehive.domain.userrole;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.infrastructure.exceptions.AppException;

@Service
public class RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	private static final String NO_SUCH_ROLE = "User role with name {0} not found";
	
	public Role getUserRoleFromDatabase(RoleName role) {
		return roleRepository.findByName(role)
				.orElseThrow(() -> new AppException(MessageFormat.format(NO_SUCH_ROLE, role.toString())));
	}

}

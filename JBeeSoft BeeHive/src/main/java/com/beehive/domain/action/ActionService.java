package com.beehive.domain.action;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ActionDTO;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.payload.UserDTO;

@Service
public class ActionService {
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private UserService userService;

	public void registerPerformedAction(Action action) {
		actionRepository.save(action);
	}
	
	public List<Action> getActionsPerformedOnHives(Set<Hive> affectedHives) {
		return actionRepository.findDistinctByAffectedHivesIn(affectedHives);
	}
	
	public ActionDTO mapToActionDTO(Action action) {
		Set<HiveDTO> affectedHives = action.getAffectedHives()
				.stream()
				.map(hiveService::mapHiveToHiveDTO)
				.collect(Collectors.toSet());
		
		UserDTO performer = userService.mapToUserDTO(action.getPerformer());
		
		return action.builderDTO()
				.withId(action.getId())
				.withDate(action.getDate())
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.build();
	}
}

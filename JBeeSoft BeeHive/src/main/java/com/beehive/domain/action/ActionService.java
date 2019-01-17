package com.beehive.domain.action;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beehive.domain.action.feeding.FeedingAction;
import com.beehive.domain.action.feeding.FeedingActionRepository;
import com.beehive.domain.action.honeycollecting.HoneyCollectingAction;
import com.beehive.domain.action.honeycollecting.HoneyCollectingActionRepository;
import com.beehive.domain.action.inspection.InspectionRepository;
import com.beehive.domain.action.treatment.TreatmentAction;
import com.beehive.domain.action.treatment.TreatmentActionRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ActionDTO;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.payload.UserDTO;

@Service
public class ActionService {
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private FeedingActionRepository feedingRepository;
	
	@Autowired 
	TreatmentActionRepository treatmentActionRepository;
	
	@Autowired
	HoneyCollectingActionRepository honeyCollectingActionRepository;
	
	@Autowired
	InspectionRepository inspectionRepository;
	
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
	
	public List<TreatmentAction> getTreatmentActionPerformedOnHives(Set<Hive> affectedHives, Date start, Date end){
		return treatmentActionRepository.findByAffectedHivesInAndDateBetween(affectedHives, start, end);
	}
	
	public List<HoneyCollectingAction> getHoneyCollectingActionPerformedOnHives(Set<Hive> affectedHives, Date start, Date end){
		return honeyCollectingActionRepository.findByAffectedHivesInAndDateBetween(affectedHives, start, end);
	}
	
	public List<FeedingAction> getFeedingActionsPerformedOnHives(Set<Hive> affectedHives, Date start, Date end){
		return feedingRepository.findByAffectedHivesInAndDateBetween(affectedHives, start, end);
	}
	
	public Long getNumberTreatmentActionPerformedOnHivesAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer){
		return treatmentActionRepository.countByAffectedHivesInAndDateBetweenAndPerformer(affectedHives, start, end, performer);
	}
	
	public Long getNumberHoneyCollectingActionPerformedOnHivesAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer){
		return honeyCollectingActionRepository.countByAffectedHivesInAndDateBetweenAndPerformer(affectedHives, start, end, performer);
	}
	
	public Long getNumberFeedingActionsPerformedOnHivesAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer){
		return feedingRepository.countByAffectedHivesInAndDateBetweenAndPerformer(affectedHives, start, end, performer);
	}
	
	public Long getNumberInspectionActionsPerformedOnHivesAndPerformer(Set<Hive> affectedHives, Date start, Date end, User performer){
		return inspectionRepository.countByAffectedHivesInAndDateBetweenAndPerformer(affectedHives, start, end, performer);
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

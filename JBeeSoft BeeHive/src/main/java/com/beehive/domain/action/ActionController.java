package com.beehive.domain.action;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.action.feeding.FeedingAction;
import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryRepository;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveRepository;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.FeedingActionRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api" + ActionController.MAIN_PATH)
public class ActionController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HiveRepository hiveRepository;
	
	@Autowired
	private ApiaryRepository apiaryRepository;
	
	@Autowired
	ActionService actionService;
	
	public static final String MAIN_PATH = "/action";
	private static final String APIARY_ID = "apiary_id";
	public static final String FEEDING_PATH = "/feeding/{" + APIARY_ID + "}";
	
	private static final String NO_SUCH_USER = "There is no user with id {0}";
	private static final String NO_SUCH_APIARY = "There is no apiary with id {0}";
	private static final String WRONG_HIVE_IDS = "There are wrong hive ids in affected hives set!";
	private static final String NOT_ALL_HIVES_BELONG_TO_APIARY = "There are hives that don't belong to specified apiary!";
	
	@PostMapping(FEEDING_PATH)
	public ApiResponse performFeedingAction(@Valid @RequestBody FeedingActionRequest feedingActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userRepository.findById(currentUser.getId()).orElseThrow(
				() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_USER, currentUser.getId())));
		
		Apiary apiary = apiaryRepository.findById(apiaryId).orElseThrow(
				() -> new IllegalArgumentException(MessageFormat.format(NO_SUCH_APIARY, apiaryId)));
		
		Set<Hive> affectedHives = hiveRepository.findAllByIdIn(feedingActionRequest.getAffectedHives());
		validateHives(affectedHives, feedingActionRequest.getAffectedHives(), apiary);
		
		FeedingAction feedingAction = FeedingAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withFeedType(feedingActionRequest.getFeedType())
				.withFeedAmount(feedingActionRequest.getFeedAmount())
				.withPrice(feedingActionRequest.getPrice())
				.build();
		
		actionService.registerPerformedAction(feedingAction);
		return new ApiResponse(true, "Action performed succesfully");
		
	}
	
	private void validateHives(Set<Hive> hives, Set<Long> ids, Apiary apiary) {
		if(hives.size() != ids.size()) {
			throw new IllegalArgumentException(WRONG_HIVE_IDS);
		} 
		
		if(!allHivesBelongsToApiary(hives, apiary)) {
			throw new IllegalAccessError(NOT_ALL_HIVES_BELONG_TO_APIARY);
		}				
	}
	
	private boolean allHivesBelongsToApiary(Set<Hive> hives, Apiary apiary) {
		return hives.stream()
				.allMatch(hive -> hive.getApiary().equals(apiary));
	}	
	
}

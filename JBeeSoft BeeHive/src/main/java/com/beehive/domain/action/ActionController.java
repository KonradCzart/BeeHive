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
import com.beehive.domain.action.honeycollecting.HoneyCollectingAction;
import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryRepository;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveRepository;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.honey.HoneyType;
import com.beehive.domain.honey.HoneyTypeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserRepository;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.FeedingActionRequest;
import com.beehive.infrastructure.payload.HoneyCollectiongActionRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api" + ActionController.MAIN_PATH)
public class ActionController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private ApiaryService apiaryService;
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private HoneyTypeService honeyTypeService;
	
	public static final String MAIN_PATH = "/action";
	private static final String APIARY_ID = "apiary_id";
	public static final String FEEDING_PATH = "/feeding/{" + APIARY_ID + "}";
	public static final String HONEY_COLLECTIONG_PATH = "/honeycollecting/{" + APIARY_ID + "}";

	
	@PostMapping(FEEDING_PATH)
	public ApiResponse performFeedingAction(@Valid @RequestBody FeedingActionRequest feedingActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userService.getUserFormDatabase(currentUser.getId());			
		Set<Hive> affectedHives = hiveService.getHivesInApiaryFromDatabase(feedingActionRequest.getAffectedHives(), apiaryId);
		
		FeedingAction feedingAction = FeedingAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withFeedType(feedingActionRequest.getFeedType())
				.withFeedAmount(feedingActionRequest.getFeedAmount())
				.withPrice(feedingActionRequest.getPrice())
				.build();
		
		actionService.registerPerformedAction(feedingAction);
		return new ApiResponse(true, "Feeding action performed succesfully");
		
	}
	
	@PostMapping(HONEY_COLLECTIONG_PATH)
	public ApiResponse performHoneyCollectiongAction(@Valid @RequestBody HoneyCollectiongActionRequest honeyCollectiongActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userService.getUserFormDatabase(currentUser.getId());
		Set<Hive> affectedHives = hiveService.getHivesInApiaryFromDatabase(honeyCollectiongActionRequest.getAffectedHives(), apiaryId);
		HoneyType honeyType = honeyTypeService.geHoneyTypeFromDatabase(honeyCollectiongActionRequest.getHoneyTypeId());
		
		HoneyCollectingAction honeyCollectiongAction = HoneyCollectingAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withHoneyType(honeyType)
				.withHoneyAmount(honeyCollectiongActionRequest.getHoneyAmount())
				.build();
		
		actionService.registerPerformedAction(honeyCollectiongAction);
		return new ApiResponse(true, "Honey collectiong action performed succesfully");
	}
	
}
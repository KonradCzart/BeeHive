package com.beehive.domain.action;

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
import com.beehive.domain.action.inspection.InspectionAction;
import com.beehive.domain.action.queenchanging.QueenChangingAction;
import com.beehive.domain.action.treatment.TreatmentAction;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.honey.HoneyType;
import com.beehive.domain.honey.HoneyTypeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.FeedingActionRequest;
import com.beehive.infrastructure.payload.HoneyCollectiongActionRequest;
import com.beehive.infrastructure.payload.InspectionActionRequest;
import com.beehive.infrastructure.payload.QueenChangingActionRequest;
import com.beehive.infrastructure.payload.TreatmentActionRequest;
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
	public static final String TREATMENT_PATH = "/treatment/{" + APIARY_ID + "}";
	public static final String INSPECTION_PATH = "/inspection/{" + APIARY_ID + "}";
	public static final String QUEEN_CHANGING_PATH = "/queenchanging/{" + APIARY_ID + "}";

	
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
	
	@PostMapping(TREATMENT_PATH)
	public ApiResponse performTreatmentAction(@Valid @RequestBody TreatmentActionRequest treatmentActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userService.getUserFormDatabase(currentUser.getId());
		Set<Hive> affectedHives = hiveService.getHivesInApiaryFromDatabase(treatmentActionRequest.getAffectedHives(), apiaryId);
		
		TreatmentAction treatmentAction = TreatmentAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withDeseaseType(treatmentActionRequest.getDeseaseType())
				.withAppliedMedicine(treatmentActionRequest.getAppliedMedicine())
				.withDose(treatmentActionRequest.getDose())
				.withPrice(treatmentActionRequest.getPrice())
				.build();
		
		actionService.registerPerformedAction(treatmentAction);
		return new ApiResponse(true, "Treatment action performed succesfully");
	}
	
	@PostMapping(INSPECTION_PATH)
	public ApiResponse performInspectionAction(@Valid @RequestBody InspectionActionRequest inspectionActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userService.getUserFormDatabase(currentUser.getId());
		Set<Hive> affectedHives = hiveService.getHivesInApiaryFromDatabase(inspectionActionRequest.getAffectedHives(), apiaryId);
		
		InspectionAction inspectionAction= InspectionAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withIsMaggotPresent(inspectionActionRequest.getIsMaggotPresent())
				.withIsLairPresent(inspectionActionRequest.getIsLairPresent())
				.withframesWithWaxFoundation(inspectionActionRequest.getFramesWithWaxFoundation())
				.withHiveStrength(inspectionActionRequest.getHiveStrength())
				.withDescription(inspectionActionRequest.getDecription())
				.build();
		
		actionService.registerPerformedAction(inspectionAction);
		return new ApiResponse(true, "Inspection action performed succesfully");
	}
	
	@PostMapping(QUEEN_CHANGING_PATH)
	public ApiResponse performQueenChangingAction(@Valid @RequestBody QueenChangingActionRequest queenChangingActionRequest, @PathVariable(name = APIARY_ID) Long apiaryId, @CurrentUser UserPrincipal currentUser) {
		User performer = userService.getUserFormDatabase(currentUser.getId());
		Set<Hive> affectedHives = hiveService.getHivesInApiaryFromDatabase(queenChangingActionRequest.getAffectedHives(), apiaryId);
		
		QueenChangingAction queenChangingAction = QueenChangingAction.builder()
				.withAffectedHives(affectedHives)
				.withPerformer(performer)
				.withDate(new Date())
				.withPrice(queenChangingActionRequest.getPrice())
				.build();
		
		actionService.registerPerformedAction(queenChangingAction);
		return new ApiResponse(true, "Queen changing action performed succesfully");
	}
	
}

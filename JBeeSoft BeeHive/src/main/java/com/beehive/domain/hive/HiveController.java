package com.beehive.domain.hive;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.action.ActionService;
import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.domain.privileges.Privilege;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ActionDTO;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.HiveDTO;
import com.beehive.infrastructure.payload.HiveRequest;
import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/hive")
public class HiveController {
	
	@Autowired
	private HiveService hiveService;
	
	@Autowired
	private HiveTypeService hiveTypeService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	private ApiaryService apiaryService;
	
	@Autowired
	private ActionService actionService;
	
	private static final String HIVE_CREATED_SUCCESFULLY_MSG = "Hive created successfully";
	private static final String HIVE_DELETED_SUCCESFULLY_MSG = "Hive deleted successfully";
	
    @PostMapping("/new")
    public ApiResponse createHive(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody HiveRequest hiveRequest) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	Apiary apiary = apiaryService.getApiaryFromDatabase(hiveRequest.getApiaryId());
    	
    	privilegeService.validateHasUserAllRequiredPermissions(user, apiary, Set.of(Privilege.APIARY_EDITING));
   	
		hiveService.createHive(hiveRequest);
		return new ApiResponse(true, HIVE_CREATED_SUCCESFULLY_MSG);
	}
    
    
    @GetMapping("/type")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getAllHiveType(@CurrentUser UserPrincipal currentUser){  	
    	return hiveService.getAllHiveType();
    }
    
    @GetMapping("/{hiveId}")
    @PreAuthorize("hasRole('USER')")
    public HiveDTO getHiveById(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	Hive hive = hiveService.getHiveFromDatabase(hiveId);
  
    	privilegeService.validateHasUserAnyOfListedPermissions(user, hive.getApiary(), Privilege.getAllAvailablePrivileges());
    	
    	return hiveService.mapHiveToHiveDTO(hive);
    }
    
    @DeleteMapping("/delete/{hiveId}/queen")
    @PreAuthorize("hasRole('USER')")
    public ApiResponse deleteQueenWithHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId){
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	Hive hive = hiveService.getHiveFromDatabase(hiveId);
    	
    	privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.HIVE_EDITING));
    	
		hiveService.deleteQueenWithHive(hiveId);
		return new ApiResponse(true, HIVE_DELETED_SUCCESFULLY_MSG);
    }
    
    @DeleteMapping("/delete/{hiveId}/all")
    @PreAuthorize("hasRole('USER')")
    public  ApiResponse deleteHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId){
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	Hive hive = hiveService.getHiveFromDatabase(hiveId);
    	
    	privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.APIARY_EDITING));
    	
		hiveService.deleteHive(hiveId);
		return new ApiResponse(true, HIVE_DELETED_SUCCESFULLY_MSG);
    }
    
    @PutMapping("/modify/{hiveId}")
    @PreAuthorize("hasRole('USER')")
    public  HiveDTO modifyHive(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId, @Valid @RequestBody HiveRequest hiveRequest) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
		Hive hive = hiveService.getHiveFromDatabase(hiveId);
		HiveType type = hiveTypeService.getHiveTypeFromDatabase(hiveRequest.getHiveTypeId());
		
		privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.HIVE_EDITING));

		hive.setBoxNumber(hiveRequest.getBoxNumber());
		hive.setName(hiveRequest.getName());
		hive.setHiveType(type);

		hive = hiveService.modifyHive(hive);
		return hiveService.mapHiveToHiveDTO(hive);
	}
    
    @GetMapping("/actions-history/{hiveId}")
    @PreAuthorize("hasRole('USER')")
    public List<ActionDTO> getActionsHistory(@CurrentUser UserPrincipal currentUser, @PathVariable Long hiveId) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
    	Hive hive = hiveService.getHiveFromDatabase(hiveId);
    	
    	privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.HIVE_STATS_READING));
    	
    	return actionService.getActionsPerformedOnHives(Set.of(hive))
    			.stream()
    			.map(actionService::mapToActionDTO)
    			.collect(Collectors.toList());
    }
}

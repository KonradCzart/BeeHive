package com.beehive.domain.bee.queen;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.privileges.Privilege;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.ApiResponse;
import com.beehive.infrastructure.payload.BeeQueenDTO;
import com.beehive.infrastructure.payload.BeeQueenRequest;
import com.beehive.infrastructure.payload.ValueResponse;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;


@RestController
@RequestMapping("/api/queen")
public class BeeQueenController {
	
	@Autowired
	BeeQueenService beeQueenService;
	
	@Autowired
	PrivilegeService privilegeService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	HiveService hiveService;
	
	@PostMapping("/new")
	@PreAuthorize("hasRole('USER')")
    public ApiResponse createBeeQueen(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BeeQueenRequest beeQueenRequest) {
		User user = userService.getUserFormDatabase(currentUser.getId());
		Hive hive = hiveService.getHiveFromDatabase(beeQueenRequest.getHiveId());
		
		privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.HIVE_EDITING));
		
    	beeQueenService.addBeeQueenToHive(beeQueenRequest);
        return new ApiResponse(true, "Queen add to hive successfully");
    }
	
	
    @GetMapping("/race")
    @PreAuthorize("hasRole('USER')")
    public List<ValueResponse> getAllQueenRace(@CurrentUser UserPrincipal currentUser){
    	return beeQueenService.getAllQueenRace();
    }
    
    @PutMapping("/modify")
    @PreAuthorize("hasRole('USER')")
    public BeeQueenDTO modifyQueen(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody BeeQueenDTO queenDTO) {
    	User user = userService.getUserFormDatabase(currentUser.getId());
		BeeQueen queen = beeQueenService.getBeeQueenFromDatabase(queenDTO.getId());
	    
		hiveService.getHiveFromDatabaseForBeeQueen(queenDTO.getId())
				.ifPresent((hive) -> privilegeService.validateHasUserAllRequiredPermissions(user, hive.getApiary(), Set.of(Privilege.HIVE_EDITING)));
		
		queen.setColor(queenDTO.getColor());
		queen.setDescription(queenDTO.getDescription());
		queen.setIsReproducting(queenDTO.getIsReproducting());
		
		queen = beeQueenService.modifyQueen(queen);
    	
    	return beeQueenService.mapBeeQueenToBeeQueenDTO(queen);
    }
    
}

package com.beehive.domain.statistics;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.apiary.Apiary;
import com.beehive.domain.apiary.ApiaryService;
import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.privileges.PrivilegeProfile;
import com.beehive.domain.privileges.PrivilegeService;
import com.beehive.domain.user.User;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.StatisticsApiaryRequest;
import com.beehive.infrastructure.payload.StatisticsContributorDTO;
import com.beehive.infrastructure.payload.StatisticsDTO;
import com.beehive.infrastructure.payload.StatisticsRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	
	
	@Autowired 
	StatisticsService statisticsService;
	
	@Autowired 
	UserService userService;
	
	@Autowired 
	HiveService hiveService;
	
	@Autowired
	ApiaryService apiaryService;
	
	@Autowired
	private PrivilegeService privilegeService;
	
	@GetMapping("/feeding/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> feedingStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesInApiaryFromDatabase(statisticsRequest.getHiveId(), apiaryId);
		
		return statisticsService.getFeedingStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	
	@GetMapping("/treatment/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> treatmentStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesInApiaryFromDatabase(statisticsRequest.getHiveId(), apiaryId);
		
		return statisticsService.getTreatmentStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	
	@GetMapping("/honey/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> honeyStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesInApiaryFromDatabase(statisticsRequest.getHiveId(), apiaryId);
		
		return statisticsService.getHoneyStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	
	@GetMapping("/contributor/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsContributorDTO> contributorStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsApiaryRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Apiary apiary = apiaryService.getApiaryFromDatabase(apiaryId);
		List<User> contributor = privilegeService.getProfilesForApiary(apiary)
				.stream()
				.map(PrivilegeProfile::getTargetUser)
				.collect(Collectors.toList());
		
		return statisticsService.getStatisticsForContributor(contributor, apiary.getHivesWithDeleted(), statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());	
		
	}
	  
}

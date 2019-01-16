package com.beehive.domain.statistics;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.beehive.domain.hive.Hive;
import com.beehive.domain.hive.HiveService;
import com.beehive.domain.user.UserService;
import com.beehive.infrastructure.payload.StatisticsDTO;
import com.beehive.infrastructure.payload.StatisticsRequest;
import com.beehive.infrastructure.security.CurrentUser;
import com.beehive.infrastructure.security.UserPrincipal;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {
	
	
	@Autowired StatisticsService statisticsService;
	
	@Autowired UserService userService;
	
	@Autowired HiveService hiveService;
	
	@GetMapping("/feeding/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> feedingStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesFromDatabase(statisticsRequest.getHiveId());
		
		return statisticsService.getFeedingStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	
	@GetMapping("/treatment/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> treatmentStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesFromDatabase(statisticsRequest.getHiveId());
		
		return statisticsService.getTreatmentStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	
	@GetMapping("/honey/{apiaryId}")
	@PreAuthorize("hasRole('USER')")
	public List<StatisticsDTO> honeyStatistics(@CurrentUser UserPrincipal currentUser, @RequestBody StatisticsRequest statisticsRequest, @PathVariable Long apiaryId){
		
		Set<Hive> hives = hiveService.getHivesFromDatabase(statisticsRequest.getHiveId());
		
		return statisticsService.getHoneyStatisticsForHives(hives, statisticsRequest.getBeginningDate(), statisticsRequest.getEndDate());
	}
	  
}

package com.studyForger.Study_Forger.Dashboard;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.studyForger.Study_Forger.Configuration.AppConstants;


@CrossOrigin(AppConstants.FRONT_END_URL)
@RestController
@RequestMapping("/api/dashboard")

public class DashBoardController{

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<DashboardResponseDto> getDashboard(@PathVariable String userId){
        DashboardResponseDto dashboardResponse = dashboardService.getDashboard(userId);
        return new ResponseEntity<>(dashboardResponse, HttpStatus.OK);
    }
}

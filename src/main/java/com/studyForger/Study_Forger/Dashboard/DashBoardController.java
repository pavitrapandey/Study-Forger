package com.studyForger.Study_Forger.Dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashBoardController{

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> getDashboard(@PathVariable String userId){
        String dashboardResponse = dashboardService.getDashboard(userId).toString();
        return new ResponseEntity<>(dashboardResponse, HttpStatus.OK);
    }
}

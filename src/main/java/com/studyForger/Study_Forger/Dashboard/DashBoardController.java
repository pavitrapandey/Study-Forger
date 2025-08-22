package com.studyForger.Study_Forger.Dashboard;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(method = "GET",description = "Get dashboard for a user")
    @ApiResponses(value={
            @ApiResponse(description = "Dashboard retrieved successfully", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    public ResponseEntity<DashboardResponseDto> getDashboard(@PathVariable String userId){
        DashboardResponseDto dashboardResponse = dashboardService.getDashboard(userId);
        return new ResponseEntity<>(dashboardResponse, HttpStatus.OK);
    }
}

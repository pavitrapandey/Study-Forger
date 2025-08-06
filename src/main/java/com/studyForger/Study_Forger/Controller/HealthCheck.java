package com.studyForger.Study_Forger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/Check")
public class HealthCheck{

    @GetMapping
    public String check(Principal principal){
        return "Hello !!"+ principal.getName();
    }
}

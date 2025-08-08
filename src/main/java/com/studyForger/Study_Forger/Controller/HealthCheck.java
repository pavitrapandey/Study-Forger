package com.studyForger.Study_Forger.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/check")
public class HealthCheck{

    @GetMapping("/route1")
    public String check1(){
        return "Hello !!" ;
    }
    @GetMapping("/route2")
    public String check2(){
        return "Hello !!" ;
    }
    @GetMapping("/route3")
    public String check3(){
        return "Hello !!";
    }
    @GetMapping("/route4")
    public String check4(){
        return "Hello !!";
    }
    @GetMapping("/route5")
    public String check5(){
        return "Hello !!";
    }
}

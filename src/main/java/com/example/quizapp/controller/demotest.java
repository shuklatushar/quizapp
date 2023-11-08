package com.example.quizapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class demotest {

    @GetMapping("demo")
    public String Demo(){
        return "Test successful";
    }
}

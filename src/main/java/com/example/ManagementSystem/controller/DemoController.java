package com.example.ManagementSystem.controller;

import com.example.ManagementSystem.model.User;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/demo")
public class DemoController {
    @PostMapping
    public String test(@RequestParam("name") String name){
       return name;
    }
    @GetMapping
    public String test(){
        return "Test";
    }
}

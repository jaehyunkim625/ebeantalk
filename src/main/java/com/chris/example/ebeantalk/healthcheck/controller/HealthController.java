package com.chris.example.ebeantalk.healthcheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String healthCheck(){
        return "ok";
    }

    @GetMapping("/sayHello")
    public ResponseDTO sayHello(){
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResult("hello");
        return responseDTO;
    }
}

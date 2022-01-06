package com.hungry.consultorang.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorld(){
        return new ResponseEntity<String>("Hello", HttpStatus.OK);
    }
}

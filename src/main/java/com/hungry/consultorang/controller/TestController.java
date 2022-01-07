package com.hungry.consultorang.controller;

import com.hungry.consultorang.model.TestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<TestModel> helloWorld(){
        return new ResponseEntity<TestModel>(new TestModel(10, "sim"), HttpStatus.OK);
    }
}

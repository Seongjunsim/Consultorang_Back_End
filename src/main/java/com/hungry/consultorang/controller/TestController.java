package com.hungry.consultorang.controller;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.model.TestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;


@Controller
public class TestController {

    @Autowired
    CommonDao commonDao;

    @GetMapping("/hello")
    public ResponseEntity<TestModel> helloWorld(){
        return new ResponseEntity<TestModel>(new TestModel(10, "sim"), HttpStatus.OK);
    }

    @GetMapping("/hi")
    public ResponseEntity<List<HashMap<String, Object>>> hiWorld(){
        return new ResponseEntity<List<HashMap<String, Object>>>(commonDao.getUser(), HttpStatus.OK);
    }
}

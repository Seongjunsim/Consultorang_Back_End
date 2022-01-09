package com.hungry.consultorang.controller;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.TestModel;
import com.hungry.consultorang.model.UserModel;
import com.hungry.consultorang.service.test.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;


@Controller
@Slf4j
public class TestController {


    TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/hello")
    public ResponseEntity<TestModel> helloWorld(){

        //return new ResponseEntity<TestModel>(new TestModel(10, "sim"), HttpStatus.OK);
        return null;
    }

    @Transactional
    @GetMapping("/getUserList")
    public ResponseEntity<RestResponse> getUserList(){

        RestResponse response = new RestResponse();

        List<ParentModel> ret = testService.getUserList();

        return new ResponseEntity<RestResponse>(response.setSuccess(ret), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/insertList")
    public ResponseEntity<String> insertList() throws Exception{

        for(int i=21; i<25; i++){
            //UserModel um = new UserModel(i, "hi", "hi", "sim");
            //commonDao.insert("common.insertUser", um);
            if(i==24)
                throw new IOException();

        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}

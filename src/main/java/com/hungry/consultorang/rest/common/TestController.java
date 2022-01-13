package com.hungry.consultorang.rest.common;

import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;


@Controller
@Slf4j
public class TestController {


    TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/hello")
    public ResponseEntity<RestResponse> helloWorld(@RequestBody UserModel param){

        log.info(param.toString());

        RestResponse ret = new RestResponse();

        return new ResponseEntity<RestResponse>(ret.setSuccess(param), HttpStatus.OK);

    }

    @Transactional
    @GetMapping("/getUserList")
    public ResponseEntity<RestResponse> getUserList() throws Exception{

        RestResponse response = new RestResponse();

        List<ParentModel> ret = testService.getUserList();

        return new ResponseEntity<RestResponse>(response.setSuccess(ret), HttpStatus.OK);
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/insertList")
    public ResponseEntity<String> insertList() throws Exception{

        for(int i=25; i<=30; i++){
            //UserModel um = new UserModel(i, "hi", "hi", "sim");
            //commonDao.insert("common.insertUser", um);
            if(i==30)
                throw new IOException();

        }

        return new ResponseEntity<String>("OK", HttpStatus.OK);
    }
}

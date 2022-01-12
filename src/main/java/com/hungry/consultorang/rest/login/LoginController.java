package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/login")
public class LoginController {
    LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/signin")
    public ResponseEntity<RestResponse> signIn(@RequestBody SignInRequestModel param) throws Exception{
        RestResponse response = new RestResponse();

        SignInResponseModel resData = loginService.signIn(param);

        return new ResponseEntity<RestResponse>(response.setSuccess(resData), HttpStatus.OK);
    }
}

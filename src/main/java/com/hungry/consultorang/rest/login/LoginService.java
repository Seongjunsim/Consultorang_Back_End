package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import com.hungry.consultorang.model.login.SignUpRequestModel;
import com.hungry.consultorang.model.login.SignUpResponseModel;

import java.util.HashMap;

public interface LoginService {

    public SignInResponseModel signIn(SignInRequestModel param) throws Exception;
    public SignUpResponseModel signUp(SignUpRequestModel param) throws Exception;
    public HashMap<String, Object> checkEmail(HashMap<String, String> param) throws Exception;

}

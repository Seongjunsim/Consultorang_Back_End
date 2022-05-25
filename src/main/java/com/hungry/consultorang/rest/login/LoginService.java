package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.model.login.*;

import java.util.HashMap;

public interface LoginService {

    public SignUpResponseModel signIn(SignInRequestModel param) throws Exception;
    public SignUpResponseModel signUp(SignUpRequestModel param) throws Exception;
    public SignUpResponseModel getUserInfo(HashMap<String, String> param) throws Exception;
    public HashMap<String, Object> checkEmail(HashMap<String, String> param) throws Exception;
    public boolean checkBusinessNum(HashMap<String, String> param) throws Exception;
}

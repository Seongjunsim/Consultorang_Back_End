package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;

public interface LoginService {

    public SignInResponseModel signIn(SignInRequestModel param) throws Exception;

}

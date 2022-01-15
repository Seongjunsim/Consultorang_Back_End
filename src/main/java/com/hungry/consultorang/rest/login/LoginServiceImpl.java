package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.common.provider.JwtTokenProvider;
import com.hungry.consultorang.model.UserModel;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import com.hungry.consultorang.model.login.SignUpRequestModel;
import com.hungry.consultorang.model.login.SignUpResponseModel;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class LoginServiceImpl implements LoginService{

    CommonDao commonDao;
    JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(CommonDao commonDao, JwtTokenProvider jwtTokenProvider) {
        this.commonDao = commonDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public SignInResponseModel signIn(SignInRequestModel param) throws Exception{
        UserModel user = (UserModel) commonDao.selectOne("login.getUser", param);
        if(user==null){
            throw new LoginException("등록되지 않은 이메일입니다.");
        }
        if(!user.getPw().equals(param.getPw())) throw new LoginException("비밀번호가 틀렸습니다.");

        String token = jwtTokenProvider.createToken(user);
        SignInResponseModel ret = SignInResponseModel.builder()
            .pw(user.getPw())
            .userId(user.getUserId())
            .email(user.getEmail())
            .businessName(user.getBusinessName())
            .token(token)
            .build();
        return ret;
    }

    @Override
    public SignUpResponseModel signUp(SignUpRequestModel param) throws Exception {
        commonDao.insert("login.insertUser", param);
        SignUpResponseModel ret = SignUpResponseModel.builder()
            .businessName(param.getBusinessName())
            .pw(param.getPw())
            .email(param.getEmail()).build();
        return ret;
    }

    @Override
    public HashMap<String, Object> checkEmail(HashMap<String, String> param) throws Exception {
        HashMap<String, Object> ret = new HashMap<>();
        int cnt = (int) commonDao.selectOne("login.checkEmail", param);

        boolean is = cnt==0;
        ret.put("isEnableEmail", is);
        return ret;
    }
}

package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.common.provider.JwtTokenProvider;
import com.hungry.consultorang.model.dto.UserModel;
import com.hungry.consultorang.model.login.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //오 진짜 ㅅ신기 이렇게 하면 롤백될때 특정 예외로 뱉음
    @Transactional(rollbackFor = LoginException.class)
    //batch 는 중간 저장 아예 다 롤백아니고 끊어서
    @Override
    public SignUpResponseModel signUp(SignUpRequestModel param) throws Exception {
        commonDao.insert("login.insertUser", param);
        int userId = (int) commonDao.selectOne("login.getUserId", param);
        param.setUserId(userId);
        commonDao.insert("login.insertBusiness", param);

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

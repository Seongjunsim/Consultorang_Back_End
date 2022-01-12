package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.model.UserModel;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import org.springframework.stereotype.Service;

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
        UserModel user = (UserModel) commonDao.selectOne("common.getUser", param);
        if(user==null){
            throw new LoginException("등록되지 않은 이메일입니다.");
        }
        if(!user.getUserPw().equals(param.getUserPw())) throw new LoginException("비밀번호가 틀렸습니다.");

        String token = jwtTokenProvider.createToken(user);
        SignInResponseModel ret = new SignInResponseModel();
        ret.setUserId(user.getUserId());
        ret.setUserEmail(user.getUserEmail());
        ret.setUserName(user.getUserName());
        ret.setToken(token);

        return ret;
    }
}

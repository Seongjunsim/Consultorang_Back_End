package com.hungry.consultorang.rest.login;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.common.provider.JwtTokenProvider;
import com.hungry.consultorang.model.dto.UserModel;
import com.hungry.consultorang.model.login.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService{

    CommonDao commonDao;
    JwtTokenProvider jwtTokenProvider;

    public LoginServiceImpl(CommonDao commonDao, JwtTokenProvider jwtTokenProvider) {
        this.commonDao = commonDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public SignUpResponseModel signIn(SignInRequestModel param) throws Exception{
        UserModel user = (UserModel) commonDao.selectOne("login.getUser", param);
        if(user==null){
            throw new LoginException("등록되지 않은 이메일입니다.");
        }
        if(!user.getPw().equals(param.getPw())) throw new LoginException("비밀번호가 틀렸습니다.");

        String token = jwtTokenProvider.createToken(user);
        SignUpResponseModel ret = (SignUpResponseModel) commonDao.selectOne("login.getUserAndBusiness", user);
        ret.setToken(token);
        return ret;
    }

    @Transactional(rollbackFor = LoginException.class)
    @Override
    public SignUpResponseModel signUp(SignUpRequestModel param) throws Exception {
        commonDao.insert("login.insertUser", param);
        int userId = (int) commonDao.selectOne("login.getUserId", param);
        param.setUserId(userId);

        commonDao.insert("login.insertBusiness", param);
        SignUpHolidayModel model = new SignUpHolidayModel();
        int businessId = (int) commonDao.selectOne("login.getBusinessId", param);
        model.setBusinessId(businessId);
        for(String h : param.getBusinessHoliday()){
            model.setHolidayCode(h);
            commonDao.batchInsert("login.insertBusinessHoliday", model);
        }
        commonDao.flushStatements();
        SignUpResponseModel ret = (SignUpResponseModel) commonDao.selectOne("getUserAndBusiness", param);
        List<String> holiday =  commonDao.selectModelList("login.getHoliday", model);
        ret.setBusinessHoliday(holiday);
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

    @Override
    public boolean checkBusinessNum(HashMap<String, String> param) throws Exception {
        HashMap<String, Object> ret = new HashMap<>();
        int cnt = (int) commonDao.selectOne("login.checkBusinessNum", param);
        return cnt==0;
    }
}

package com.hungry.consultorang.rest.cost;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.LoginException;
import com.hungry.consultorang.common.provider.JwtTokenProvider;
import com.hungry.consultorang.model.cost.AllCostRequestModel;
import com.hungry.consultorang.model.cost.AllCostResponseModel;
import com.hungry.consultorang.model.cost.InsertCostRequestModel;
import com.hungry.consultorang.model.cost.InsertCostResponseModel;
import com.hungry.consultorang.model.dto.UserModel;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import org.springframework.stereotype.Service;

@Service
public class CostServiceImpl {


    CommonDao commonDao;

    public CostServiceImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }


    public AllCostResponseModel getAllCost(AllCostRequestModel param) throws Exception{

    }

    public InsertCostResponseModel insertCost(InsertCostRequestModel param) throws Exception {

    }


}

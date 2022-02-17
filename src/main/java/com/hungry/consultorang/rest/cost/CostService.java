package com.hungry.consultorang.rest.cost;

import com.hungry.consultorang.model.cost.AllCostRequestModel;
import com.hungry.consultorang.model.cost.AllCostResponseModel;
import com.hungry.consultorang.model.cost.InsertCostRequestModel;
import com.hungry.consultorang.model.cost.InsertCostResponseModel;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import com.hungry.consultorang.model.login.SignUpRequestModel;
import com.hungry.consultorang.model.login.SignUpResponseModel;

import java.util.HashMap;

public interface CostService {

    public AllCostResponseModel getAllCost(AllCostRequestModel param) throws Exception;
    public InsertCostResponseModel insertCost(InsertCostRequestModel param) throws Exception;
}

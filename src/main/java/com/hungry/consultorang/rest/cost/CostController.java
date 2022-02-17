package com.hungry.consultorang.rest.cost;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.cost.AllCostRequestModel;
import com.hungry.consultorang.model.cost.InsertCostRequestModel;
import com.hungry.consultorang.model.login.SignInRequestModel;
import com.hungry.consultorang.model.login.SignInResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/cost")
public class CostController {

    @PostMapping("/allcost")
    public ResponseEntity<RestResponse> getAllCost(@RequestBody AllCostRequestModel param) throws Exception{
        RestResponse response = new RestResponse();

        AllCostRequestModel resData = CostService.getAllCost;

        return new ResponseEntity<RestResponse>(response.setSuccess(resData), HttpStatus.OK);
    }

    @PostMapping("/allcost")
    public ResponseEntity<RestResponse> insertCost(@RequestBody InsertCostRequestModel param) throws Exception{
        RestResponse response = new RestResponse();

        InsertCostRequestModel resData = CostService.insertCost;

        return new ResponseEntity<RestResponse>(response.setSuccess(resData), HttpStatus.OK);
    }

}

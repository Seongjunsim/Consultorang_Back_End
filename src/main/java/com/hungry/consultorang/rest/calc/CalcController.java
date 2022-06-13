package com.hungry.consultorang.rest.calc;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.calc.CalcHistoryModel;
import com.hungry.consultorang.model.calc.SetHistoryRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/calc")
public class CalcController {

    private CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @RequestMapping(value = "/setHistory")
    public ResponseEntity<RestResponse> setHistory(@RequestBody SetHistoryRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();

        calcService.setHistory(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);

    }

    @RequestMapping(value = "/getHistory")
    public ResponseEntity<RestResponse> getHistory(@RequestBody HashMap<String, Object> param) throws Exception{
        RestResponse ret = new RestResponse();

        List<CalcHistoryModel> l = calcService.getHistory(param);
        return new ResponseEntity<RestResponse>(ret.setSuccess(l), HttpStatus.OK);
    }
}

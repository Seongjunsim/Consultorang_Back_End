package com.hungry.consultorang.rest.state;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.state.GetComparisionResponseModel;
import com.hungry.consultorang.model.state.GetComparisonRequestModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/state")
public class StateController {
    StateService stateService;

    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @PostMapping("/setAnual")
    public ResponseEntity<RestResponse> setAnual() throws Exception{
        RestResponse response = new RestResponse();

        //stateService.setAnual();

        return new ResponseEntity<RestResponse>(response.setSuccess(), HttpStatus.OK);

    }

    @PostMapping("/getComparison")
    public ResponseEntity<RestResponse> getComparison(@RequestBody GetComparisonRequestModel param)
        throws Exception{
        RestResponse ret = new RestResponse();
        GetComparisionResponseModel data = stateService.getComparison(param);

        return new ResponseEntity<RestResponse>(ret.setSuccess(data), HttpStatus.OK);
    }


}

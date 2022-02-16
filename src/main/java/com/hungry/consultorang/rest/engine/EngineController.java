package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.account.ParsingExcelFileModel;
import com.hungry.consultorang.model.account.ParsingExcelFileResponseModel;
import com.hungry.consultorang.model.engine.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value="/engine")
public class EngineController {

    EngineService engineService;

    public EngineController(EngineService engineService) {
        this.engineService = engineService;
    }


    @PostMapping("/getCatEngine")
    public ResponseEntity<RestResponse> getCatEngine
        (@RequestBody CatEngineRequestModel param) throws Exception{

        RestResponse res = new RestResponse();
        CatEngineResponseModel ret = engineService.getCatEngine(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(ret), HttpStatus.OK);
    }

    @PostMapping("/getCatList")
    public ResponseEntity<RestResponse> getCatList(@RequestBody HashMap<String, Object> param) throws Exception{
        RestResponse res = new RestResponse();

        List<Object> data = engineService.getCatList(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(data), HttpStatus.OK);
    }

}

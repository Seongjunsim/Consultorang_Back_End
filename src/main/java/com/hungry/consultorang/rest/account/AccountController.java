package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.account.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping(value = "/account")
public class AccountController {

    AccountService accountService;
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/insertExcel")
    public ResponseEntity<RestResponse> insertExcel(
        @ModelAttribute ParsingExcelFileModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        ParsingExcelFileResponseModel ret = accountService.parsingExcelFile(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(ret), HttpStatus.OK);
    }

    @PostMapping("/updateCatType")
    public ResponseEntity<RestResponse> updateCatType(@RequestBody UpdateCatTypeRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        accountService.updateCatType(param);
        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);

    }

    @PostMapping("/getCatMenuList")
    public ResponseEntity<RestResponse> getCatMenuList(@RequestBody GetCatMenuListRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        List<GetCatMenuListResponseModel> data = accountService.getCatMenuList(param);
        return new ResponseEntity<RestResponse>(res.setSuccess(data), HttpStatus.OK);
    }

    @PostMapping("/updateMenuList")
    public ResponseEntity<RestResponse> updateMenuList(@RequestBody UpdateMenuListRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        accountService.updateMenuList(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);
    }

    @PostMapping("/insertExpend")
    public ResponseEntity<RestResponse> insertExpend(@RequestBody InsertExpendRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        accountService.insertExpend(param);
        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);
    }

    @PostMapping("/getTotalHistoryList")
    public ResponseEntity<RestResponse> getTotalHistoryList(@RequestBody TotalHistoryRequestModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        List<Object> data = accountService.getTotalHistoryList(param);
        return new ResponseEntity<RestResponse>(res.setSuccess(data), HttpStatus.OK);
    }


}

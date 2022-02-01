package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.engine.CatEngineRequestModel;
import com.hungry.consultorang.model.engine.CatEngineResponseModel;
import com.hungry.consultorang.model.engine.ParsingExcelFileModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

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

    @PostMapping("/insertExcel")
    public ResponseEntity<RestResponse> insertExcel(
        @ModelAttribute ParsingExcelFileModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        engineService.parsingExcelFile(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);
    }


    @PostMapping("/test")
    public ResponseEntity<RestResponse> test(
        @ModelAttribute ParsingExcelFileModel param) throws Exception{

        String sourceFileNm = param.getMultipartFile().getOriginalFilename();

        File df;
        String dfnm;
        // TODO: 2022-02-01 file util 에 현 시간 기준으로 파일 생성 ~~_

        df = new File("C:/Users/USER/Desktop/test/"+sourceFileNm);
        param.getMultipartFile().transferTo(df);

        return null;
    }


}

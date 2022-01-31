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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

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
    public ResponseEntity<RestResponse> insertExcel(@RequestBody ParsingExcelFileModel param)
        throws Exception{
        RestResponse res = new RestResponse();
        param.setFileNm("C:/Users/USER/Documents/test.xlsx");
        engineService.parsingExcelFile(param);

        return new ResponseEntity<RestResponse>(res.setSuccess(), HttpStatus.OK);
    }


    @PostMapping("/test")
    public ResponseEntity<RestResponse> test() throws Exception{

        FileInputStream file = new FileInputStream("C:/Users/USER/Documents/test.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);

        int rowindex = 0;
        int columnindex = 0;

        XSSFSheet sheet = workbook.getSheetAt(1);
        int rows=sheet.getPhysicalNumberOfRows();
        for(rowindex=0;rowindex<=rows;rowindex++){ //행을읽는다
            XSSFRow row=sheet.getRow(rowindex);
            if(row !=null){//셀의 수
                int cells=row.getPhysicalNumberOfCells();
                for(columnindex=0; columnindex<=cells; columnindex++){
                    //셀값을 읽는다
                    XSSFCell cell=row.getCell(columnindex);
                    String value="";
                    //셀이 빈값일경우를 위한 널체크
                    if(cell==null){
                        continue;
                    }
                    else{ //타입별로 내용 읽기
                        CellType type = cell.getCellType();
                        switch (type.name()){
                            case "FORMULA":
                            case "NUMERIC":
                                value=cell.getNumericCellValue()+"";
                                break;
                            case "STRING":
                                value=cell.getStringCellValue()+"";
                                break;
                            case "BOOLEAN":
                                value=cell.getBooleanCellValue()+"";
                                break;
                        }
                    }
                    System.out.println(rowindex+"row : "+columnindex+"coll :" +
                        new String(value.getBytes(StandardCharsets.UTF_8), "utf-8"));
                }
            }
        }

        return null;
    }


}

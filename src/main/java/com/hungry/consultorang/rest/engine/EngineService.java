package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.model.account.ParsingExcelFileModel;
import com.hungry.consultorang.model.account.ParsingExcelFileResponseModel;
import com.hungry.consultorang.model.engine.*;

import java.util.HashMap;
import java.util.List;

public interface EngineService {
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception;


    public List<Object> getCatList(HashMap<String,Object> param)
        throws Exception;

    public EngineSolResponseModel getEngineSolList(EngineSolRequestModel param)
        throws Exception;

}

package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.model.engine.*;

import java.util.HashMap;
import java.util.List;

public interface EngineService {
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception;

    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;

    public List<Object> getCatList(HashMap<String,Object> param)
        throws Exception;

}

package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.model.engine.CatEngineRequestModel;
import com.hungry.consultorang.model.engine.CatEngineResponseModel;
import com.hungry.consultorang.model.engine.ParsingExcelFileModel;

import java.util.List;

public interface EngineService {
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception;

    public List<Object> parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;

}

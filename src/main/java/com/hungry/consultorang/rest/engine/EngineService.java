package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.model.engine.CatEngineRequestModel;
import com.hungry.consultorang.model.engine.CatEngineResponseModel;
import com.hungry.consultorang.model.engine.ParsingExcelFileModel;

public interface EngineService {
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception;

    public void parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;

}

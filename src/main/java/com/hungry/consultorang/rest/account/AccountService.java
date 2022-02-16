package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.model.account.ParsingExcelFileModel;
import com.hungry.consultorang.model.account.ParsingExcelFileResponseModel;
import com.hungry.consultorang.model.account.UpdateCatTypeRequestModel;

public interface AccountService {
    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;

    public void updateCatType(UpdateCatTypeRequestModel param)
        throws Exception;
}

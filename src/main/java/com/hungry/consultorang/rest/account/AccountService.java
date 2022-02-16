package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.model.account.ParsingExcelFileModel;
import com.hungry.consultorang.model.account.ParsingExcelFileResponseModel;

public interface AccountService {
    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;
}

package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.model.account.*;

import java.util.List;

public interface AccountService {
    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param)
        throws Exception;

    public void updateCatType(UpdateCatTypeRequestModel param)
        throws Exception;

    public List<GetCatMenuListResponseModel> getCatMenuList(GetCatMenuListRequestModel param)
        throws Exception;
}

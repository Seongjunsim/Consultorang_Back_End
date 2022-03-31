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

    public void updateMenuList(UpdateMenuListRequestModel param)
        throws Exception;

    public void insertExpend(InsertExpendRequestModel param)
        throws Exception;

    public AllCostResponseModel getAllCost(AllCostRequestModel param)
        throws Exception;

    public InsertCostResponseModel insertCost(InsertCostRequestModel param)
        throws Exception;

    public List<Object> getTotalHistoryList(TotalHistoryRequestModel param)
        throws Exception;
    public void insertEtcMenu(List<InsertEtcMenuRequestModel> param)
        throws Exception;
    public List<Object> getEtcMenuList(GetEtcMenuListRequestModel param)
        throws Exception;
    public void deleteEtcMenu(List<DeleteEtcMenuModel> param)
        throws Exception;

    void insertMemo(InsertMemoRequestModel param);

    List<SaleExpendYmdModel> getSaleExpendYmd(GetSaleExpendYmdRequestModel param)
        throws Exception;

    GetCurPrevSaleExpendResponseModel getCurPrevSaleExpend(GetCurPrevSaleExpendRequestModel param)
        throws Exception;
}

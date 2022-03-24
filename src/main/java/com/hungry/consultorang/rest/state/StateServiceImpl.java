package com.hungry.consultorang.rest.state;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.model.state.GetComparisionResponseModel;
import com.hungry.consultorang.model.state.GetComparisonRequestModel;
import com.hungry.consultorang.model.state.SIHModel;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashMap;

@Service
public class StateServiceImpl implements StateService{

    CommonDao commonDao;

    public StateServiceImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Override
    public void setAnual() throws Exception{

        ExcelParserUtil parserUtil = new ExcelParserUtil("C:/Users/USER/Desktop/test/anual.xlsx",1);

        int idx = 2;
        int size = parserUtil.getRowSize();
        HashMap<String, Object> req = new HashMap<>();
        for(;idx<size; idx++){
            String[] types = parserUtil.getCellData(idx, 0).split("/");
            req.put("stCd", types[0]);
            req.put("ssCd", "null");
            if(types.length>=2) req.put("ssCd", types[1]);
            int totalSale = getInt(parserUtil.getCellData(idx, 1));
            int foodCost = getInt(parserUtil.getCellData(idx,3));
            int employeeCost = getInt(parserUtil.getCellData(idx,4));
            int hireCost = getInt(parserUtil.getCellData(idx,5));
            int taxCost = getInt(parserUtil.getCellData(idx,6));
            int familyEmployeeCost = getInt(parserUtil.getCellData(idx,7));
            int employerCost = getInt(parserUtil.getCellData(idx,8));
            int etcCost = getInt(parserUtil.getCellData(idx,9));
            int saleProfit = getInt(parserUtil.getCellData(idx,10));
            req.put("totalSale", totalSale);req.put("foodCost", foodCost);
            req.put("employeeCost", employeeCost); req.put("hireCost", hireCost);
            req.put("taxCost", taxCost); req.put("familyEmployeeCost", familyEmployeeCost);
            req.put("employerCost", employerCost); req.put("etcCost", etcCost);
            req.put("saleProfit", saleProfit);
            commonDao.batchInsert("state.insertPlCompareData", req);
        }
        commonDao.flushStatements();
    }
    private int getInt(String temp){
        return (int) Double.parseDouble(temp);
    }

    @Override
    public GetComparisionResponseModel getComparison(GetComparisonRequestModel param) throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("userId", param.getUserId()); req.put("saleYm", param.getYm());
        req.put("startYmd", param.getYm()+"00"); req.put("endYmd",param.getYm()+"99");
        int totalSale = (int) commonDao.selectOne("account.getTotalSales", req);
        int totalEtcSale = (int) commonDao.selectOne("account.getTotalEtcSales", req);
        totalSale += totalEtcSale;
        req.put("expendType", "ET001");
        int totalIngre = (int) commonDao.selectOne("account.getTotalExpend", req);
        req.put("expendType", "ET002");
        int totalHuman = (int) commonDao.selectOne("account.getTotalExpend", req);

        SIHModel user = SIHModel.builder()
            .totalSale(totalSale)
            .foodCost(totalIngre)
            .humanCost(totalHuman).build();
        HashMap<String, String> businessCd = (HashMap<String, String>) commonDao.selectOne("state.getBusinessCd", req);
        businessCd.put("businessSale", generateSaleCd(totalSale));

        req.clear();

        req.put("stCd", businessCd.get("businessSize"));
        SIHModel size = (SIHModel) commonDao.selectOne("state.getCompareData",req);
        req.put("stCd", businessCd.get("businessType"));
        req.put("ssCd", businessCd.get("businessIngre"));
        SIHModel business = (SIHModel) commonDao.selectOne("state.getCompareData",req);
        req.put("stCd", businessCd.get("businessSale"));
        SIHModel sale = (SIHModel) commonDao.selectOne("state.getCompareData",req);


        GetComparisionResponseModel ret = GetComparisionResponseModel.builder()
            .sameBusiness(business)
            .sameSale(sale)
            .sameSize(size)
            .userModel(user)
            .updateDate("2020"+param.getYm().substring(4,6)).build();

        return ret;
    }

    private String generateSaleCd(int totalSale){
        if(totalSale < 416){
            return "SS001";
        }else if(totalSale < 832){
            return "SS002";
        }else if(totalSale < 4160){
            return "SS003";
        }else{
            return "SS004";
        }
    }
}

package com.hungry.consultorang.rest.calc;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.response.RestResponse;
import com.hungry.consultorang.model.calc.CalcHistoryModel;
import com.hungry.consultorang.model.calc.IngreModel;
import com.hungry.consultorang.model.calc.SetHistoryRequestModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
public class CalcServiceImpl implements CalcService{

    CommonDao commonDao;

    public CalcServiceImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setHistory(SetHistoryRequestModel param) throws Exception{
        commonDao.batchDelete("calc.deleteHistory", param);
        commonDao.flushStatements();

        for(CalcHistoryModel model : param.getData()){
            model.setUserId(param.getUserId());
            commonDao.batchInsert("calc.insertHistory", model);
        }
        commonDao.flushStatements();

        List<CalcHistoryModel> list = commonDao.selectModelList("calc.getHistory", param);

        for(int i=0; i<list.size(); i++){
            String id = list.get(i).getCalcId();

            for(IngreModel m : param.getData().get(i).getIngreArr()){
                m.setCalcId(id);
                commonDao.insert("calc.insertIngre", m);
            }
            commonDao.flushStatements();
        }

    }

    @Override
    public List<CalcHistoryModel> getHistory(HashMap<String, Object> param) throws Exception{

        List<CalcHistoryModel> l = commonDao.selectModelList("calc.getHistory", param);

        for(CalcHistoryModel cm : l){
            param.put("calcId", cm.getCalcId());
            cm.setIngreArr(commonDao.selectModelList("calc.getIngre", param));
        }

        return l;
    }
}

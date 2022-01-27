package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.model.dto.CatMenuModel;
import com.hungry.consultorang.model.engine.CatEngineRequestModel;
import com.hungry.consultorang.model.engine.CatEngineResponseModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class EngineServiceImpl implements EngineService{

    CommonDao commonDao;

    public EngineServiceImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Override
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception {

        int size = (int)commonDao.selectOne("engine.getCatMenuSize", param);
        size = size - (size/5);
        HashMap<String, Object> reqMap = new HashMap<>();
        reqMap.put("size", size);
        reqMap.put("userId", param.getUserId());
        reqMap.put("saleYm", param.getSaleYm());
        reqMap.put("catId", param.getCatId());

        List<Object> menuLists =
             commonDao.selectList("engine.getCatMenu", reqMap);

        int totalSale = 0;
        int totalCnt = 0;

        for(Object o : menuLists){
            CatMenuModel cmm = (CatMenuModel) o;
            totalSale+=(cmm.getMenuCost()*cmm.getSaleQuantity());
            totalCnt+=cmm.getSaleQuantity();

        }


        return null;
    }
}

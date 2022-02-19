package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.EngineException;
import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.config.EnvSet;
import com.hungry.consultorang.model.account.ParsingExcelFileModel;
import com.hungry.consultorang.model.account.ParsingExcelFileResponseModel;
import com.hungry.consultorang.model.dto.MenuModel;
import com.hungry.consultorang.model.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EngineServiceImpl implements EngineService{

    CommonDao commonDao;
    EnvSet envSet;

    public EngineServiceImpl(CommonDao commonDao, EnvSet envSet) {
        this.commonDao = commonDao;
        this.envSet = envSet;
    }

    @Override
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception {

        int size = (int) commonDao.selectOne("engine.getCatMenuSize", param);
        size = size-(Math.round((float)size/5));
        param.setSize(size);

        CatEngineTotalModel total = (CatEngineTotalModel) commonDao.selectOne("engine.getCatTotal", param);

        List<MenuModel> first = new LinkedList<>();
        List<MenuModel> second = new LinkedList<>();
        List<MenuModel> thrid = new LinkedList<>();

        List<Object> list = commonDao.selectList("engine.getCatMenu", param);

        for(Object o : list){
            MenuModel m = (MenuModel) o;
            switch (m.getMenuEngineCd()){
                case "ME001":
                    first.add(m);
                    break;
                case "ME002":
                case "ME003":
                    second.add(m);
                    break;
                case "ME004":
                    thrid.add(m);
                    break;
            }
        }
        CatEngineResponseModel retModel = CatEngineResponseModel.builder()
            .totalCnt(total.getTotalCnt())
            .totalSale(total.getTotalSale())
            .first(first)
            .second(second)
            .third(thrid).build();

       return retModel;
    }

    @Override
    public List<Object> getCatList(HashMap<String, Object> param) throws Exception {
        return commonDao.selectList("engine.getCatList", param);
    }
}

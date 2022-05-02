package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.EngineException;
import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.config.EnvSet;
import com.hungry.consultorang.config.SolutionSet;
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
    SolutionSet solutionSet;
    public EngineServiceImpl(CommonDao commonDao, EnvSet envSet, SolutionSet solutionSet) {
        this.commonDao = commonDao;
        this.envSet = envSet;
        this.solutionSet=solutionSet;
    }

    @Override
    public CatEngineResponseModel getCatEngine(CatEngineRequestModel param)
        throws Exception {

        int size = (int) commonDao.selectOne("engine.getCatMenuSize", param);
        size = size-(Math.round((float)size/5));
        param.setSize(size);

        CatEngineTotalModel total = (CatEngineTotalModel) commonDao.selectOne("engine.getCatTotal", param);

        List<EngineMenuModel> first = new LinkedList<>();
        List<EngineMenuModel> second = new LinkedList<>();
        List<EngineMenuModel> thrid = new LinkedList<>();

        List<Object> list = commonDao.selectList("engine.getCatMenu", param);

        for(Object o : list){
            EngineMenuModel m = (EngineMenuModel) o;
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

    @Override
    public EngineSolResponseModel getEngineSolList(EngineSolRequestModel param) throws Exception {

        String totalSol="";
        switch (param.getMedalType()){
            case 1:
                totalSol =  solutionSet.getGoldTotal();
                break; case 2: totalSol = solutionSet.getSilverTotal();
                break; case 3: totalSol = solutionSet.getBronzeTotal();
                break;
        }
        List<Object> solutions = new LinkedList<>();
        if(param.isHasMenu()){
            solutions = commonDao.selectList("engine.getEngineSolList", param);
        }else{
            String solContent = "";
            if(param.getMedalType()==1)solContent=solutionSet.getGoldNo();
            else if(param.getMedalType()==2)solContent=solutionSet.getSilverNo();
            else if(param.getMedalType()==3)solContent=solutionSet.getBronzeNo();
            SolutionModel solModel = new SolutionModel();
            solModel.setSolTitle("");
            solModel.setSolContent(solContent);
            solModel.setImgId(0);
            solutions.add(solModel);
        }
        EngineSolResponseModel esrm = EngineSolResponseModel.builder()
            .medalType(param.getMedalType())
            .solutions(solutions)
            .totalSol(totalSol).build();


        return esrm;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeMonthlyEngineSolution() throws Exception {

        commonDao.batchDelete("deleteMonthlySol");
        commonDao.flushStatements();
        HashMap<String, Object> req = new HashMap<>();
        for(int medalType=1; medalType<=3 ;medalType++){

            req.put("medalType", medalType);

            List<MonthlySolModel> l = commonDao.<MonthlySolModel>selectModelList("getEngineSolListByType",req);

            for(int i=0;i<=1; i++){
                int random = (int)(Math.random()*100);
                int idx = random % l.size();
                commonDao.batchInsert("insertMonthlySol", l.get(idx));
                l.remove(idx);
            }
        }
        commonDao.flushStatements();
        return;
    }
}

package com.hungry.consultorang.rest.engine;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.EngineException;
import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.config.EnvSet;
import com.hungry.consultorang.model.dto.CatMenuModel;
import com.hungry.consultorang.model.dto.MenuModel;
import com.hungry.consultorang.model.engine.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

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

        int size = (int) commonDao.selectOne("engine.getMenuSize", param);
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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param) throws Exception {

        int userId = param.getUserId();

        // 해당 연월의 데이터가 있는지 파악 => 있다면 예외 호출
        int size = (int) commonDao.selectOne("engine.getMenuSize", param);
        if(size!=0){
           throw new EngineException("해당 연월에 이미 등록된 데이터가 있습니다.");
        }

        //엑셀 파일 생성
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String sourceFileNm = sdf.format(new Date())+"_"+param.getUserId()+"_"+
            param.getMultipartFile().getOriginalFilename();

        File df = new File(envSet.getExcelPath()+sourceFileNm);
        param.getMultipartFile().transferTo(df);

        ExcelParserUtil parserUtil = new ExcelParserUtil(envSet.getExcelPath()+sourceFileNm, envSet.getExcelSheetNum());

        int rowSize = parserUtil.getRowSize();

        int catId = 0; int menuId = 0;

        int row = 6; int totalCnt=0; int totalSale=0;

        int catCnt=0; int catSale=0;

        int aveCnt=0; int aveSale=0;

        int menuSize=0; int menuSaleMax=0; int menuSaleMin=0; int menuCntMax=0; int menuCntMin=0;

        String catNm="";
        HashMap<String, Object> reqParam = new HashMap<>();
        try{
            while(row<=rowSize){
                int colSize = parserUtil.getColSize(row);

                if(row==6) { // total sum
                    totalCnt =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getCnt()));
                    totalSale =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getSale()));
                    row++;
                    continue;
                }

                String temp = parserUtil.getCellData(row,0).trim();
                String what = envSet.getCategory();
                if(temp.contains(envSet.getCategory())){//카테고리
                    catCnt =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getCnt()));
                    catSale =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getSale()));
                    catNm =
                        temp.split(":")[1];
                    HashMap<String, Integer> hm = getMenuSizeMinMax(row, parserUtil);
                    menuSize=hm.get("size");
                    menuSaleMax=hm.get("maxSale");
                    menuSaleMin=hm.get("minSale");
                    menuCntMax=hm.get("maxCnt");
                    menuCntMin=hm.get("minCnt");

                    if(menuSize!=0){
                        aveCnt = catCnt/menuSize;
                        aveSale = catSale/menuSize;

                        reqParam.put("catId", ++catId);
                        reqParam.put("userId", userId);
                        reqParam.put("catNm", catNm);
                        reqParam.put("saleYm", param.getSaleYm());
                        commonDao.insert("engine.insertCat", reqParam);
                        reqParam.clear();
                    }
                }else{ //menu
                    menuId =
                        (int)Double.parseDouble(parserUtil.getCellData(row,0));
                    int saleQuantity =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getCnt()));
                    int menuCost =
                        (int)Double.parseDouble(parserUtil.getCellData(row,envSet.getMenuCost()));
                    int menuSale =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getSale()));
                    String menuNm =
                        parserUtil.getCellData(row, envSet.getMenuNm()).trim();
                    if(menuCost>0){
                        int conMargin = calcPercent(menuSale, aveSale, menuSaleMax, menuSaleMin);
                        int popularity = calcPercent(saleQuantity, aveCnt, menuCntMax, menuCntMin);

                        String menuEngineCd=generateCd(conMargin, popularity);

                        reqParam.put("userId", userId);
                        reqParam.put("saleYm", param.getSaleYm());
                        reqParam.put("catId", catId);
                        reqParam.put("menuId", menuId);
                        reqParam.put("saleQuantity", saleQuantity);
                        reqParam.put("menuCost", menuCost);
                        reqParam.put("menuNm", menuNm);
                        reqParam.put("popularity", popularity);
                        reqParam.put("contributionMargin", conMargin);
                        reqParam.put("menuEngineCd", menuEngineCd);
                        commonDao.insert("engine.insertMenu", reqParam);
                        reqParam.clear();
                    }
                }
                row++;
            }
        }catch (Exception e){
            throw new EngineException(e.getMessage());
        }finally {//파싱 후 바로 엑셀 삭제
            parserUtil.close();
            df.delete();
        }
        reqParam.put("saleYm", param.getSaleYm());
        reqParam.put("userId", param.getUserId());

        List<Object> list = commonDao.selectList("engine.getCatList", reqParam);
        ParsingExcelFileResponseModel retModel = ParsingExcelFileResponseModel.builder()
            .userId(userId)
            .saleYm(param.getSaleYm())
            .menuList(list)
            .build();
        return retModel;
    }
    private HashMap<String, Integer> getMenuSizeMinMax(int row, ExcelParserUtil util){
        HashMap<String, Integer> ret = new HashMap<>();
        int size=0;
        int minSale = Integer.MAX_VALUE;
        int minCnt = Integer.MAX_VALUE;
        int maxSale = 0;
        int maxCnt = 0;
        while(++row <= util.getRowSize()){
            if(util.getCellData(row,0)==null||util.getCellData(row,0).trim().contains(envSet.getCategory()))
                break;
            int sale = (int) Double.parseDouble(util.getCellData(row, envSet.getSale()));
            int cnt = (int) Double.parseDouble(util.getCellData(row,envSet.getCnt()));
            int cost = (int) Double.parseDouble(util.getCellData(row, envSet.getMenuCost()));
            if(cost==0) continue;
            if(cnt >= maxCnt) maxCnt = cnt;
            if(cnt <= minCnt) minCnt = cnt;
            if(sale >= maxSale) maxSale = sale;
            if(sale <= minSale) minSale = sale;
            size++;
        }
        ret.put("size", size);
        ret.put("maxCnt", maxCnt);
        ret.put("minCnt", minCnt);
        ret.put("maxSale", maxSale);
        ret.put("minSale", minSale);
        return ret;
    }

    private int calcPercent(int num, int ave, int maxNum, int minNum){
        int ret=0;

        float left=  num>ave?maxNum:ave;
        float right= num>ave?ave:minNum;
        float valuePercent = ((num-right)/((left-right)*2))*100f;
        if(num>ave) valuePercent+=50f;
        ret = (int)valuePercent;

        return ret;
    }

    private String generateCd(int saleP, int cntP){
        if(saleP>=50 && cntP >=50) return "ME001";
        else if(saleP < 50 && cntP >= 50) return "ME003";
        else if(saleP >=50 && cntP < 50) return "ME002";
        else return "ME004";
    }
}
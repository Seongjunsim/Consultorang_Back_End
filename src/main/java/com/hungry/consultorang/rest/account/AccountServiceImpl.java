package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.EngineException;
import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.config.EnvSet;
import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.account.*;
import com.hungry.consultorang.model.dto.CmMmOperandModel;
import com.hungry.consultorang.model.dto.MenuModel;
import com.hungry.consultorang.model.dto.UpdateMenuModel;
import com.hungry.consultorang.rest.engine.EngineServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AccountServiceImpl implements AccountService{
    private CommonDao commonDao;
    private EnvSet envSet;

    public AccountServiceImpl(CommonDao commonDao, EnvSet envSet) {
        this.commonDao = commonDao;
        this.envSet = envSet;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateCatType(UpdateCatTypeRequestModel param) throws Exception {
        HashMap<String, Object> req = new HashMap<>();
        req.put("userId", param.getUserId());
        req.put("saleYm", param.getSaleYm());
        for(CatTypeModel ctm : param.getCatList()){
            req.put("catId", ctm.getCatId());
            req.put("catType", ctm.getCatType());
            commonDao.update("account.updateCatType", req);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ParsingExcelFileResponseModel parsingExcelFile(ParsingExcelFileModel param) throws Exception {

        int userId = param.getUserId();

        // 해당 연월의 데이터가 있는지 파악 => 있다면 예외 호출
        int size = (int) commonDao.selectOne("account.getMenuSize", param);
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
        int menuMaxId = (int)commonDao.selectOne("account.getMenuMaxId", param);
        int catMaxId = (int)commonDao.selectOne("account.getCatMaxId",param);
        int row = 6;

        double mm=0; double cm=0;

        int menuSize=0; double menuSaleMax=0; double menuSaleMin=0; double menuCntMax=0; double menuCntMin=0;

        String catNm="";
        HashMap<String, Object> reqParam = new HashMap<>();
        try{
            while(row<=rowSize){

                if(row==6) { // total sum
                    row++;
                    continue;
                }

                String temp = parserUtil.getCellData(row,0).trim();
                if(temp.contains(envSet.getCategory())){//카테고리

                    catNm =
                        temp.split(":")[1].trim();
                    HashMap<String, Double> hm = getMenuSizeMinMax(row, parserUtil);
                    menuSize=(int)Math.round(hm.get("size"));
                    menuSaleMax=(double)hm.get("maxSale");
                    menuSaleMin=(double)hm.get("minSale");
                    menuCntMax=(double)hm.get("maxCnt");
                    menuCntMin=(double)hm.get("minCnt");

                    if(menuSize!=0){
                        mm = 1d / (menuSize - Math.round((double) menuSize/5)) * 0.5d;
                        cm = getCm(row, menuSize, parserUtil);

                        reqParam.put("userId", userId);
                        reqParam.put("catNm", catNm);
                        Object o = commonDao.selectOne("getCatId", reqParam);
                        catId = o==null?0:(int)o;
                        if(catId==0){
                            catId = catMaxId++;
                            reqParam.put("catId", catId);
                            commonDao.insert("account.insertCat", reqParam);
                        }
                        reqParam.clear();
                    }
                }else{ //menu
                    int saleQuantity =
                        (int)Double.parseDouble(parserUtil.getCellData(row, envSet.getCnt()));
                    int menuCost =
                        (int)Double.parseDouble(parserUtil.getCellData(row,envSet.getMenuCost()));
                    String menuNm =
                        parserUtil.getCellData(row, envSet.getMenuNm()).trim();
                    reqParam.put("userId", userId);
                    reqParam.put("menuNm", menuNm);
                    Object o = commonDao.selectOne("account.getMenuId", reqParam);
                    menuId = o==null?0:(int)o;
                    if(menuId==0){
                        menuId = menuMaxId++;
                        reqParam.put("menuId", menuId);
                        commonDao.insert("account.insertMenu",reqParam);
                    }
                    double salePercent = Double.parseDouble(parserUtil.getCellData(row, envSet.getSalePercent()));
                    double cntPercent = Double.parseDouble(parserUtil.getCellData(row, envSet.getCntPercent()));

                    if(menuCost>0){
                        int conMargin = calcPercent(salePercent, cm, menuSaleMax, menuSaleMin);
                        int popularity = calcPercent(cntPercent, mm, menuCntMax, menuCntMin);

                        String menuEngineCd=generateCd(conMargin, popularity);

                        reqParam.put("userId", userId);
                        reqParam.put("saleYm", param.getSaleYm());
                        reqParam.put("catId", catId);
                        reqParam.put("menuId", menuId);
                        reqParam.put("saleQuantity", saleQuantity);
                        reqParam.put("menuCost", menuCost);
                        reqParam.put("popularity", popularity);
                        reqParam.put("contributionMargin", conMargin);
                        reqParam.put("menuEngineCd", menuEngineCd);
                        commonDao.insert("account.insertSale", reqParam);
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

        List<Object> list = commonDao.selectList("account.getUndefinedCatList", reqParam);
        ParsingExcelFileResponseModel retModel = ParsingExcelFileResponseModel.builder()
            .userId(userId)
            .saleYm(param.getSaleYm())
            .menuList(list)
            .build();
        return retModel;
    }

    private HashMap<String, Double> getMenuSizeMinMax(int row, ExcelParserUtil util){
        HashMap<String, Double> ret = new HashMap<>();
        int size=0;
        double minSalePercent = 1d;
        double minCntPercent = 1d;
        double maxSalePercent = 0d;
        double maxCntPercent = 0d;
        while(++row <= util.getRowSize()){
            if(util.getCellData(row,0)==null||util.getCellData(row,0).trim().contains(envSet.getCategory()))
                break;
            double salePercent = Double.parseDouble(util.getCellData(row, envSet.getSalePercent()));
            double cntPercent = Double.parseDouble(util.getCellData(row,envSet.getCntPercent()));
            int cost = (int) Double.parseDouble(util.getCellData(row, envSet.getMenuCost()));
            if(cost==0) continue;
            if(cntPercent >= maxCntPercent) maxCntPercent = cntPercent;
            if(cntPercent <= minCntPercent) minCntPercent = cntPercent;
            if(salePercent >= maxSalePercent) maxSalePercent = salePercent;
            if(salePercent <= minSalePercent) minSalePercent = salePercent;
            size++;
        }
        ret.put("size", (double)size);
        ret.put("maxCnt", maxCntPercent);
        ret.put("minCnt", minCntPercent);
        ret.put("maxSale", maxSalePercent);
        ret.put("minSale", minSalePercent);
        return ret;
    }

    private double getCm(int row, int size, ExcelParserUtil util){
        int abandonSize = Math.round((float)size / 5f);
        PriorityQueue<CmPair> pq = new PriorityQueue<>();
        double catSalePercent = Double.parseDouble(util.getCellData(row, envSet.getSalePercent()));
        while(++row <= util.getRowSize()){
            if(util.getCellData(row,0)==null||util.getCellData(row,0).trim().contains(envSet.getCategory()))
                break;
            int cost = (int) Double.parseDouble(util.getCellData(row, envSet.getMenuCost()));
            double salePercent = Double.parseDouble(util.getCellData(row,envSet.getSalePercent()));
            if(pq.size()<abandonSize)
                pq.add(new CmPair(salePercent, cost));
            else{
                if(!pq.isEmpty() && pq.peek().cost < cost) continue;
                pq.poll();
                pq.add(new CmPair(salePercent, cost));
            }
        }
        while(!pq.isEmpty()) catSalePercent -= pq.poll().salePercent;

        return catSalePercent / (size-abandonSize);
    }

    private int calcPercent(double num, double ave, double maxNum, double minNum){
        int ret=0;

        double left=  num>ave?maxNum:ave;
        double right= num>ave?ave:minNum;
        double valuePercent = ((num-right)/((left-right)*2))*100f;
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

    private class CmPair implements Comparable<CmPair>{
        public double salePercent;
        public int cost;

        public CmPair(double salePercent, int cost) {
            this.salePercent = salePercent;
            this.cost = cost;
        }

        @Override
        public int compareTo(CmPair o) {
            return this.cost <= o.cost ? 1 : -1 ;
        }
    }

    @Override
    public List<GetCatMenuListResponseModel> getCatMenuList(GetCatMenuListRequestModel param) throws Exception {
        List<Object> catList =  commonDao.selectList("account.getCatList", param);
        List<GetCatMenuListResponseModel> ret = new LinkedList<>();
        for(Object cat : catList){
            HashMap<String, Object> c = (HashMap<String, Object>) cat;
            c.put("saleYm", param.getSaleYm());
            List<ParentModel> menuList = commonDao.selectModelList("account.getMenuList", c);
            GetCatMenuListResponseModel model = GetCatMenuListResponseModel.builder()
                .catId((int)c.get("catId"))
                .catNm((String)c.get("catNm"))
                .menuList(menuList).build();
            ret.add(model);
        }
        return ret;
    }

    @Transactional(rollbackFor = AccountException.class)
    @Override
    public void updateMenuList(UpdateMenuListRequestModel param) throws Exception {
        int userId = param.getUserId();
        String saleYm = param.getSaleYm();
        HashMap<String, Object> req = new HashMap<>();
        req.put("userId", userId);
        req.put("saleYm", saleYm);
        req.put("catId", param.getCatId());
        for(UpdateMenuModel umm : param.getMenuList()){
            req.put("menuId", umm.getMenuId());
            req.put("menuCost", umm.getMenuCost());
            req.put("saleQuantity", umm.getSaleQuantity());
            commonDao.update("account.updateSale", req);
        }

        int size = (int) commonDao.selectOne("engine.getCatMenuSize", req);
        size -= Math.round((double) size/5);
        req.put("size", size);
        double mm = (1/ (double)size) * 0.5d;
        CmMmOperandModel operand = (CmMmOperandModel) commonDao.selectOne("account.getCmMmOperand", req);
        List<Object> menuList = commonDao.selectList("engine.getCatMenu",req);
        for(Object o : menuList){
            MenuModel m = (MenuModel) o;
            double menuQ = (double)m.getSaleQuantity()/(double) operand.getTotalSaleQuantity();
            int popularity = calcPercent(menuQ, mm, operand.getMaxMm(), operand.getMinMm());
            int contributionMargin = calcPercent(m.getSale(), operand.getCm(), operand.getMaxSale(), operand.getMinSale());
            String menuEngineCd = generateCd(contributionMargin, popularity);

            req.put("menuCost", m.getMenuCost());
            req.put("saleQuantity", m.getSaleQuantity());
            req.put("menuId", m.getMenuId());
            req.put("popularity", popularity);
            req.put("contributionMargin", contributionMargin);
            req.put("menuEngineCd", menuEngineCd);
            commonDao.update("account.updateSale", req);
        }

    }

    @Override
    public AllCostResponseModel getAllCost(AllCostRequestModel param) throws Exception {
        return null;
    }

    @Override
    public InsertCostResponseModel insertCost(InsertCostRequestModel param) throws Exception {
        return null;
    }
}
package com.hungry.consultorang.rest.account;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.common.exception.EngineException;
import com.hungry.consultorang.common.factory.CompanyExcelParserFactory;
import com.hungry.consultorang.common.parser.CompanyExcelParser;
import com.hungry.consultorang.common.parser.PosPowerExcelParser;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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


        //엑셀 파일 생성
        String pattern = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        String sourceFileNm = sdf.format(new Date())+"_"+param.getUserId()+"_"+
            param.getMultipartFile().getOriginalFilename();

        File df = new File(envSet.getExcelPath()+sourceFileNm);
        param.getMultipartFile().transferTo(df);

        ExcelParserUtil parserUtil = new ExcelParserUtil(envSet.getExcelPath()+sourceFileNm, 0);

        CompanyExcelParserFactory factory = new CompanyExcelParserFactory(parserUtil);
        CompanyExcelParser cep = factory.generateParser(param.getParserType());

        HashMap<String, Object> reqParam = new HashMap<>();
        HashMap<String, List<MenuModel>> menuList = cep.getMenuList();
        HashMap<String, Integer> historyList = cep.getHistoryList();
        //parsing sales history

        HashMap<String, Object> historyReqParam = new HashMap<>();
        historyReqParam.put("userId", userId);
        historyReqParam.put("startYmd", param.getSaleYm()+"00");
        historyReqParam.put("endYmd", param.getSaleYm()+"99");
        List<Object> hsl =  commonDao.selectList("account.getHistorySet", historyReqParam);
        HashSet<String> historySet = new HashSet<>();
        for(Object o : hsl){
            historySet.add((String) o);
        }
        for(String saleYmd : historyList.keySet()){
            historyReqParam.put("saleYmd", saleYmd);
            historyReqParam.put("saleVal", historyList.get(saleYmd));

            if(historySet.contains(saleYmd)){
                commonDao.batchUpdate("account.updateHistory", historyReqParam);
            }else{
                commonDao.batchInsert("account.insertHistory", historyReqParam);
            }
        }

        //parsing sales data
        parserUtil.chageSheetNum(1);

        int catId = 0; int menuId = 0;
        int menuMaxId = (int)commonDao.selectOne("account.getMenuMaxId", param);
        int catMaxId = (int)commonDao.selectOne("account.getCatMaxId",param);

        reqParam.put("userId", userId);
        reqParam.put("saleYm", param.getSaleYm());
        try{
            for(String catNm : menuList.keySet()){
                reqParam.put("catNm", catNm);
                Object o = commonDao.selectOne("getCatId", reqParam);
                catId = o==null?0:(int)o;
                if(catId==0){
                    catId = catMaxId++;
                    reqParam.put("catNm", catNm);
                    reqParam.put("catId", catId);
                    commonDao.batchInsert("account.insertCat", reqParam);
                }

                for(MenuModel menuModel : menuList.get(catNm)){
                    reqParam.put("menuNm", menuModel.getMenuNm());
                    o = commonDao.selectOne("account.getMenuId", reqParam);
                    menuId = o==null?0:(int)o;
                    if(menuId==0){
                        menuId = menuMaxId++;
                        reqParam.put("menuId", menuId);
                        reqParam.put("menuNm", menuModel.getMenuNm());
                        commonDao.batchInsert("account.insertMenu",reqParam);
                    }
                    menuModel.setMenuId(menuId);
                    menuModel.setUserId(userId);
                    menuModel.setSaleYm(param.getSaleYm());
                    menuModel.setCatId(catId);
                    if(commonDao.selectOne("account.getMenu", menuModel)==null)
                        commonDao.batchInsert("account.insertSale", menuModel);
                    else
                        commonDao.batchUpdate("account.updateSale",menuModel);
                }
            }
        }catch (Exception e){
            throw new EngineException(e.getMessage());
        }finally {//파싱 후 바로 엑셀 삭제
            parserUtil.close();
            df.delete();
            commonDao.flushStatements();
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
            commonDao.batchUpdate("account.updateSale", req);
        }

        commonDao.flushStatements();


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
            commonDao.batchUpdate("account.updateSale", req);
        }
        commonDao.flushStatements();

    }

    @Override
    public void insertExpend(List<InsertExpendRequestModel> param) throws Exception {
        for(InsertExpendRequestModel model : param)
            commonDao.batchInsert("account.insertExpend", model);
        commonDao.flushStatements();
    }

    @Override
    public List<Object> getTotalHistoryList(TotalHistoryRequestModel param) throws Exception {
        return commonDao.selectList("account.getTotalHistoryList", param);
    }

    @Override
    public AllCostResponseModel getAllCost(AllCostRequestModel param) throws Exception {
        return null;
    }

    @Override
    public InsertCostResponseModel insertCost(InsertCostRequestModel param) throws Exception {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertEtcMenu(List<InsertEtcMenuRequestModel> param) throws Exception {
        for(InsertEtcMenuRequestModel model : param){
            commonDao.batchInsert("account.insertEtcMenu", model);
        }
        commonDao.flushStatements();
    }

    @Override
    public List<Object> getEtcMenuList(GetEtcMenuListRequestModel param) throws Exception {
        if(param.getStartYmd()==null || param.getStartYmd().equals("")){
            param.setStartYmd("0");
        }
        if(param.getEndYmd()==null || param.getEndYmd().equals("")){
            param.setEndYmd("99999999");
        }
        return commonDao.selectList("account.getEtcMenu", param);
    }

    @Override
    public void deleteEtcMenu(List<DeleteEtcMenuModel> param) throws Exception {
        for(DeleteEtcMenuModel model : param){
            commonDao.batchDelete("account.deleteEtcMenu", model);
        }
        commonDao.flushStatements();
    }

    @Override
    public void insertMemo(InsertMemoRequestModel param) {
        commonDao.insert("account.insertMemo", param);
    }

    @Override
    public List<SaleExpendYmdModel> getSaleExpendYmd(GetSaleExpendYmdRequestModel param) throws Exception {
        List<SaleExpendYmdModel> list = new LinkedList<>();
        HashSet<String> sales = new HashSet<>();
        HashSet<String> expends = new HashSet<>();

        List<Object> s = commonDao.selectList("account.getSaleYmd", param);
        List<Object> e = commonDao.selectList("account.getExpendYmd", param);

        for(Object o : s){
            sales.add((String) o);
        }
        for(Object o : e){
            expends.add((String) o);
        }

        String ymd = param.getStartYmd();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        while(!ymd.equals(param.getEndYmd())){

            SaleExpendYmdModel model = SaleExpendYmdModel.builder()
                .ymd(ymd)
                .expend(expends.contains(ymd))
                .sale(sales.contains(ymd)).build();
            list.add(model);

            Date date = format.parse(ymd);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            ymd = format.format(calendar.getTime());
        }


        return list;
    }

    @Override
    public GetCurPrevSaleExpendResponseModel getCurPrevSaleExpend(GetCurPrevSaleExpendRequestModel param) throws Exception {

        String currentYm = param.getYm();
        HashMap<String, Object> reqParam = new HashMap<>();
        //current
        reqParam.put("userId", param.getUserId());
        reqParam.put("saleYm", param.getYm());
        reqParam.put("startYmd", param.getYm()+"00");
        reqParam.put("endYmd", param.getYm()+"99");
        Object o = commonDao.selectOne("getTotalSales", reqParam);
        int sales = o==null?0:(int)o;
        o = commonDao.selectOne("getTotalEtcSales", reqParam);
        sales+=o==null?0:(int)o;
        o = commonDao.selectOne("getTotalExpend", reqParam);
        int expends = o==null?0:(int)o;
        SaleExpendModel cur = new SaleExpendModel();
        cur.setTotalSale(sales); cur.setTotalExpend(expends);

        //prev
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date d = format.parse(currentYm);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH, -1);
        String prevYm = format.format(c.getTime());
        reqParam.put("saleYm", prevYm);
        reqParam.put("startYmd", prevYm+"00");
        reqParam.put("endYmd", prevYm+"99");
        o = commonDao.selectOne("getTotalSales", reqParam);
        sales = o==null?0:(int)o;
        o = commonDao.selectOne("getTotalEtcSales", reqParam);
        sales+=o==null?0:(int)o;
        o = commonDao.selectOne("getTotalExpend", reqParam);
        expends = o==null?0:(int)o;
        SaleExpendModel prev = new SaleExpendModel();
        prev.setTotalSale(sales); prev.setTotalExpend(expends);

        GetCurPrevSaleExpendResponseModel ret = new GetCurPrevSaleExpendResponseModel();
        ret.setCur(cur);
        ret.setPrev(prev);

        return ret;
    }
}

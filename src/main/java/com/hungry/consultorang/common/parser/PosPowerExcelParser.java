package com.hungry.consultorang.common.parser;

import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.model.dto.MenuModel;
import com.hungry.consultorang.model.dto.SaleHistoryModel;
import com.hungry.consultorang.model.parser.CategoryParserModel;
import com.hungry.consultorang.model.parser.MenuParserModel;
import com.hungry.consultorang.rest.account.AccountServiceImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class PosPowerExcelParser extends CompanyExcelParser{
    private final int ID=0, CAT=1,MENU_NM=2, MENU_COST=3, CNT=4, CNT_PERCENT=5, SALE=6
        ,SALE_PERCENT=7;
    private final int YMD=0, SALE_VAL=3;
    private final int MENU_START_ROW=6;
    private final int HISTORY_SHEET = 0;
    private final int HISTORY_START_ROW=10;
    private final int MENU_CATEGORY_SHEET = 1;
    private final String CATEGORY = "카테고리";


    public PosPowerExcelParser(ExcelParserUtil excelParserUtil) throws Exception{
        super.excelParserUtil = excelParserUtil;
        int row = 0;
        int rowSize = 0;

        // history
        excelParserUtil.chageSheetNum(HISTORY_SHEET);
        row = HISTORY_START_ROW;
        rowSize = excelParserUtil.getRowSize();

        while(row<=rowSize){
            String temp = excelParserUtil.getCellData(row, YMD);
            if(!temp.contains("-")){
                row++;
                continue;
            }
            String saleYmd = temp.trim().replaceAll("-", "");
            String vs = excelParserUtil.getCellData(row, SALE_VAL);
            int value =  vs.contains("-") ?
                0 : (int)Double.parseDouble(vs);

            if(historyMap.containsKey(saleYmd)){
                historyMap.put(saleYmd, historyMap.get(saleYmd)+value);
            }else{
                historyMap.put(saleYmd, value);
            }
            row++;
        }

        //menu & category
        excelParserUtil.chageSheetNum(MENU_CATEGORY_SHEET);
        row = MENU_START_ROW;
        rowSize = excelParserUtil.getRowSize();
        String catNm="";

        int menuSize=0;
        double menuSaleMax=0;
        double menuSaleMin=0;
        double menuCntMax=0;
        double menuCntMin=0;
        double mm=0;
        double cm=0;

        while(row<=rowSize){
            if(row==MENU_START_ROW){
                // TODO: 2022-03-02 해당 엑셀이 맞는 엑셀인지 확인
                row++;
                continue;
            }
            String temp = excelParserUtil.getCellData(row,ID).trim();
            if(temp.contains(CATEGORY)){//카테고리
                catNm = temp.split(":")[1].trim();
                HashMap<String, Double> hm = getMenuSizeMinMax(row, excelParserUtil);
                menuSize = (int) Math.round(hm.get("size"));
                menuSaleMax=(double)hm.get("maxSale");
                menuSaleMin=(double)hm.get("minSale");
                menuCntMax=(double)hm.get("maxCnt");
                menuCntMin=(double)hm.get("minCnt");

                if(menuSize == 0){
                    row++;
                    continue;
                }
                mm = 1d / (menuSize - Math.round((double) menuSize/5)) * 0.5d;
                cm = getCm(row, menuSize, excelParserUtil);

                menuList.put(catNm, new LinkedList<>());

            }else{ //menu
                String menuNm =
                    excelParserUtil.getCellData(row, MENU_NM).trim();
                int saleQuantity =
                    (int)Double.parseDouble(excelParserUtil.getCellData(row, CNT));
                int menuCost =
                    (int)Double.parseDouble(excelParserUtil.getCellData(row,MENU_COST));

                double salePercent = Double.parseDouble(excelParserUtil.getCellData(row, SALE_PERCENT));
                double cntPercent = Double.parseDouble(excelParserUtil.getCellData(row, CNT_PERCENT));

                if(menuCost>0){
                    int conMargin = calcPercent(salePercent, cm, menuSaleMax, menuSaleMin);
                    int popularity = calcPercent(cntPercent, mm, menuCntMax, menuCntMin);

                    String menuEngineCd=generateCd(conMargin, popularity);

                    MenuModel mpm = MenuModel.builder()
                        .menuNm(menuNm)
                        .saleQuantity(saleQuantity)
                        .menuCost(menuCost)
                        .popularity(popularity)
                        .contributionMargin(conMargin)
                        .menuEngineCd(menuEngineCd)
                        .build();

                    menuList.get(catNm).add(mpm);
                }
            }
            row++;
        }

    }

    private HashMap<String, Double> getMenuSizeMinMax(int row, ExcelParserUtil util){
        HashMap<String, Double> ret = new HashMap<>();
        int size=0;
        double minSalePercent = 1d;
        double minCntPercent = 1d;
        double maxSalePercent = 0d;
        double maxCntPercent = 0d;
        while(++row <= util.getRowSize()){
            if(util.getCellData(row,0)==null||util.getCellData(row,0).trim().contains(CATEGORY))
                break;
            double salePercent = Double.parseDouble(util.getCellData(row, SALE_PERCENT));
            double cntPercent = Double.parseDouble(util.getCellData(row,CNT_PERCENT));
            int cost = (int) Double.parseDouble(util.getCellData(row, MENU_COST));
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
        double catSalePercent = Double.parseDouble(util.getCellData(row, SALE_PERCENT));
        while(++row <= util.getRowSize()){
            if(util.getCellData(row,0)==null||util.getCellData(row,0).trim().contains(CATEGORY))
                break;
            int cost = (int) Double.parseDouble(util.getCellData(row, MENU_COST));
            double salePercent = Double.parseDouble(util.getCellData(row,SALE_PERCENT));
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


}

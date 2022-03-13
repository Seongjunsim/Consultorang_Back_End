package com.hungry.consultorang.common.parser;

import com.hungry.consultorang.common.util.ExcelParserUtil;
import com.hungry.consultorang.model.dto.MenuModel;
import com.hungry.consultorang.model.dto.SaleHistoryModel;
import com.hungry.consultorang.model.parser.CategoryParserModel;
import com.hungry.consultorang.rest.account.AccountServiceImpl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CompanyExcelParser {

    protected ExcelParserUtil excelParserUtil;
    protected HashMap<String, CategoryParserModel> catList = new HashMap<>();
    protected HashMap<String, List<MenuModel>> menuList = new HashMap<>();
    protected HashMap<String, Integer> historyMap = new HashMap<>();

    public HashMap<String, CategoryParserModel> getCatList() {
        return catList;
    }

    public HashMap<String, List<MenuModel>> getMenuList() {
        return menuList;
    }

    public HashMap<String, Integer> getHistoryList() {
        return historyMap;
    }

    protected class CmPair implements Comparable<CmPair>{
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

    protected int calcPercent(double num, double ave, double maxNum, double minNum){
        int ret=0;

        double left=  num>ave?maxNum:ave;
        double right= num>ave?ave:minNum;
        double valuePercent = ((num-right)/((left-right)*2))*100f;
        if(num>ave) valuePercent+=50f;
        ret = (int)valuePercent;

        return ret;
    }

    protected String generateCd(int saleP, int cntP){
        if(saleP>=50 && cntP >=50) return "ME001";
        else if(saleP < 50 && cntP >= 50) return "ME003";
        else if(saleP >=50 && cntP < 50) return "ME002";
        else return "ME004";
    }
}

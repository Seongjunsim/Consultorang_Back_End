package com.hungry.consultorang.common.factory;


import com.hungry.consultorang.common.parser.CompanyExcelParser;
import com.hungry.consultorang.common.parser.ParserType;
import com.hungry.consultorang.common.parser.PosPowerExcelParser;
import com.hungry.consultorang.common.util.ExcelParserUtil;

public class CompanyExcelParserFactory {

    ExcelParserUtil excelParserUtil;

    public CompanyExcelParserFactory(ExcelParserUtil excelParserUtil) {
        this.excelParserUtil = excelParserUtil;
    }


    public CompanyExcelParser generateParser(String type) throws Exception{
        ParserType parserType = ParserType.getParserType(type);
        switch (parserType){
            case POS_POWER:
                return new PosPowerExcelParser(excelParserUtil);
            case COMPANY_ONE:
                break;
        }
        return null;
    }
}

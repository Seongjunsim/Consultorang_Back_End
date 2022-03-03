package com.hungry.consultorang.common.parser;

import com.hungry.consultorang.common.util.ExcelParserUtil;

import java.util.List;

public interface ExcelParser {
    List<Object> getCategoryList()
        throws Exception;
    List<Object> getMenuList()
        throws Exception;
    List<Object> getSaleHistory()
        throws Exception;

}

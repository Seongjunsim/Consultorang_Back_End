package com.hungry.consultorang.rest.calc;

import com.hungry.consultorang.model.calc.CalcHistoryModel;
import com.hungry.consultorang.model.calc.SetHistoryRequestModel;

import java.util.HashMap;
import java.util.List;

public interface CalcService {
    void setHistory(SetHistoryRequestModel param) throws Exception;
    List<CalcHistoryModel> getHistory(HashMap<String, Object> param) throws Exception;

}

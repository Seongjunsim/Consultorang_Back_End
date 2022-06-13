package com.hungry.consultorang.model.calc;


import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class CalcHistoryModel extends ParentModel {
    private int userId;
    private String calcId;
    private String menuImg;
    private String menuName;
    private String date;
    private String costOfOne;
    private List<IngreModel> ingreArr;
}

package com.hungry.consultorang.model.calc;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class SetHistoryRequestModel extends ParentModel {
    private int userId;
    private List<CalcHistoryModel> data;
}

package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class TotalHistoryModel extends ParentModel {
    String historyType;
    String specificType;
    String typeNm;
    String ymd;
    int val;
}

package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class InsertExpendRequestModel extends ParentModel {
    int userId;
    String expendYmd;
    String expendType;
    int expendCost;
    String expendWho;
}

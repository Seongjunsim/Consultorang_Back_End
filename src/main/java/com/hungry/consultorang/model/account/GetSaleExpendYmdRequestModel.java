package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetSaleExpendYmdRequestModel extends ParentModel {
    int userId;
    String startYmd;
    String endYmd;
}

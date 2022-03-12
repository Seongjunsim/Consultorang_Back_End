package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class InsertEtcMenuRequestModel extends ParentModel {
    int userId;
    String saleYmd;
    String menuNm;
    int menuSale;
}

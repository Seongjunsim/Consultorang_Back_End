package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AccountMenuModel extends ParentModel {
    private int menuId;
    private String menuNm;
    private int menuCost;
    private int saleQuantity;
}

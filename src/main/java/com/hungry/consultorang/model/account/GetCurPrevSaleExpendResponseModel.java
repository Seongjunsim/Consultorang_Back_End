package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetCurPrevSaleExpendResponseModel extends ParentModel {
    SaleExpendModel cur;
    SaleExpendModel prev;
}

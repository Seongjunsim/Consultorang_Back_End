package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CatMenuModel extends ParentModel {
    int userId;
    String saleYm;
    int catId;
    int menuId;
    int saleQuantity;
    int menuCost;
}

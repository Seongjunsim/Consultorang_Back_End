package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UpdateMenuModel extends ParentModel {
    int menuId;
    int saleQuantity;
    int menuCost;
}

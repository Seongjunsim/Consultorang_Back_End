package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class MenuModel extends ParentModel {
    int userId;
    String saleYm;
    int catId;
    int menuId;
    String menuNm;
    int menuCost;
    int saleQuantity;
    int sale;
    int popularity;
    int contributionMargin;
    String menuEngineCd;

}

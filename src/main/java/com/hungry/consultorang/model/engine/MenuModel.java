package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class MenuModel extends ParentModel {
    int menuId;
    String menuNm;
    int menuCost;
    int menuSaleCnt;
    int popularPercent;
    int valuePercent;

}

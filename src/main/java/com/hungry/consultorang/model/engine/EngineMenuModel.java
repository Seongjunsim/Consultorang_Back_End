package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class EngineMenuModel extends ParentModel {
    int menuId;
    String menuNm;
    int saleQuantity;
    int menuCost;
    int sale;
    int popularity;
    int contributionMargin;
    String menuEngineCd;
}

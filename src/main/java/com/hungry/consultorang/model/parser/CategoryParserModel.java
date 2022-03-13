package com.hungry.consultorang.model.parser;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class CategoryParserModel extends ParentModel {
    String nm;
    double menuSaleMax;
    double menuSaleMin;
    double menuCntMax;
    double menuCntMin;
    double cm;
    double mm;
}

package com.hungry.consultorang.model.parser;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class MenuParserModel extends ParentModel {
    String nm;
    int cost;
    int saleQuantity;
    double cntPercent;
    double salePercent;
}

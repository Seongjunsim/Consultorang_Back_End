package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CmMmOperandModel extends ParentModel {
    int totalSaleQuantity;
    double minMm;
    double maxMm;
    int minSale;
    int maxSale;
    double cm;
}

package com.hungry.consultorang.model.cost;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
public class InsertCostRequestModel {
    int userId;
    String saleYm;
    String costType;
    int cost;
}


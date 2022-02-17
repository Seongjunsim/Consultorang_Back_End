package com.hungry.consultorang.model.cost;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class InsertCostResponseModel {
    int userId;
    String saleYm;
    String costType;
    int cost;
}

package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class SaleHistoryModel extends ParentModel {
    int userId;
    String saleYmd;
    int saleVal;
}

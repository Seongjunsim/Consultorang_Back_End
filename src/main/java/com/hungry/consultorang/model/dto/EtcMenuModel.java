package com.hungry.consultorang.model.dto;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class EtcMenuModel extends ParentModel {
    int etcId;
    String saleYmd;
    String menuNm;
    String menuSale;
}

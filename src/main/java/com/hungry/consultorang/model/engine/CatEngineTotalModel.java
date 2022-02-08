package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CatEngineTotalModel extends ParentModel {
    int totalCnt;
    int totalSale;
}

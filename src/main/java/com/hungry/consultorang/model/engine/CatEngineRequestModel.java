package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CatEngineRequestModel extends ParentModel {
    int userId;
    int catId;
    String saleYm;
}

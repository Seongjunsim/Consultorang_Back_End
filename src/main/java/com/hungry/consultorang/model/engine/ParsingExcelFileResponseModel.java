package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ParsingExcelFileResponseModel extends ParentModel {
    int catId;
    int userId;
    String saleYm;
    String catNm;
}

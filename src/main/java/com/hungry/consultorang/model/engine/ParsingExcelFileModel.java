package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.poifs.property.Parent;

@Data
@EqualsAndHashCode
public class ParsingExcelFileModel extends ParentModel {
    String saleYm;
    int userId;
    String fileNm;
}

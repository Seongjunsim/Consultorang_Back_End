package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.poi.poifs.property.Parent;

@Data
@EqualsAndHashCode
public class ParsingExcelFileModel extends ParentModel {
    String sale_ym;
    int user_id;
    String fileNm;
}

package com.hungry.consultorang.model.state;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class GetComparisonRequestModel extends ParentModel {
    int userId;
    String ym;
}

package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Builder
public class EngineSolResponseModel extends ParentModel {
    int medalType;
    String totalSol;
    List<Object> solutions;
}

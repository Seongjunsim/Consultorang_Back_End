package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class SolutionModel extends ParentModel {
    String solTitle;
    String solContent;
    int imgId;
}

package com.hungry.consultorang.model.state;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class SIHModel extends ParentModel {
    long totalSale;
    long foodCost;
    long humanCost;
}

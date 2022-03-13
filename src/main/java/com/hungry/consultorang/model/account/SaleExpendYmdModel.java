package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class SaleExpendYmdModel extends ParentModel {
    String ymd;
    boolean sale;
    boolean expend;
}

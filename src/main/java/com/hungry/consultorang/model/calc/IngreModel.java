package com.hungry.consultorang.model.calc;


import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class IngreModel extends ParentModel {
    private String ingreId;
    private String calcId;
    private int amount;
    private String amountUnit;
    private String ingreName;
    private int price;
    private String unit;
    private String amountUsed;

}

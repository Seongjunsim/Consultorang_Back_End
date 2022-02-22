package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode
public class GetCatMenuListRequestModel extends ParentModel {
    private int userId;
    private String saleYm;
    private String catType;
}

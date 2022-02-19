package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@Data
@EqualsAndHashCode
public class UpdateCatTypeRequestModel extends ParentModel {
    private int userId;
    private String saleYm;
    private List<CatTypeModel> catList;
}

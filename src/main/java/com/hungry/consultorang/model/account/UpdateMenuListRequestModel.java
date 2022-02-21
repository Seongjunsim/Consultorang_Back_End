package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class UpdateMenuListRequestModel extends ParentModel {
    int userId;
    String saleYm;
    int catId;
    List<Object> menuList;
}

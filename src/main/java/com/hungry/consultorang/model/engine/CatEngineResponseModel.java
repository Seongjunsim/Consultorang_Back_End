package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class CatEngineResponseModel extends ParentModel {
    int catId;
    int catNm;
    int catSales;
    int catSaleCnt;
    List<MenuModel> menuList;
}

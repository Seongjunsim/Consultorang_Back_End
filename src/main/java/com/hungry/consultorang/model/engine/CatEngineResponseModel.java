package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CatEngineResponseModel extends ParentModel {
    int catId;
    String catNm;
    int catSales;
    int catSaleCnt;
    List<MenuModel> menuList;
}

package com.hungry.consultorang.model.engine;

import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.dto.MenuModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CatEngineResponseModel extends ParentModel {
    int totalCnt;
    int totalSale;
    List<MenuModel> first;
    List<MenuModel> second;
    List<MenuModel> third;
}

package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
@Builder
public class GetCatMenuListResponseModel extends ParentModel {
    private int catId;
    private String catNm;
    private List<ParentModel> menuList;
}

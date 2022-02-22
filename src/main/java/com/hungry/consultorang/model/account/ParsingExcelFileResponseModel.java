package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class ParsingExcelFileResponseModel extends ParentModel {
    int userId;
    String saleYm;
    List<Object> menuList;
}

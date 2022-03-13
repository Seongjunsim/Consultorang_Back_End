package com.hungry.consultorang.model.account;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

@Data
@EqualsAndHashCode
public class TotalHistoryRequestModel extends ParentModel {
    int userId;
    String startYmd = "0";
    String endYmd = "99999999";
    String historyType;
    String specificType;
}

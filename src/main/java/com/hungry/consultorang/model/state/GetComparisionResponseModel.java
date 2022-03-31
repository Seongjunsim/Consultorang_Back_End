package com.hungry.consultorang.model.state;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class GetComparisionResponseModel extends ParentModel {
    String updateDate;
    SIHModel sameBusiness;
    SIHModel sameSale;
    SIHModel sameSize;
    SIHModel userModel;
}

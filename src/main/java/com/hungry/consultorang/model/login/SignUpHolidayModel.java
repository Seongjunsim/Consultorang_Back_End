package com.hungry.consultorang.model.login;


import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpHolidayModel extends ParentModel {
    private int businessId;
    private String holidayCode;
}

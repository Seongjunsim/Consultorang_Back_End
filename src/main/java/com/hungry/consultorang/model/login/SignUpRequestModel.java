package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpRequestModel extends ParentModel {
    private String businessName;
    private String email;
    private String pw;
    private String phone;
    private String businessNum;
    private String businessCd;
    private String serviceYn;
}

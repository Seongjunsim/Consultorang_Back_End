package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpRequestModel extends ParentModel {
    private int userId;
    private String email;
    private String pw;
    private String phone;
    private String serviceYn;

    private String businessNum;
    private String businessName;
    private String businessType;
    private String businessIngre;
    private String businessCookway;
    private int businessAlready;
    private int businessStaff;
    private String businessSize;
    private int businessStart;
    private int businessEnd;
}

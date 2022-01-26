package com.hungry.consultorang.model.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungry.consultorang.model.ParentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserModel extends ParentModel {
    int userId;
    String email;
    String pw;
    String phone;
    String serviceYn;
    Date regDt;
}

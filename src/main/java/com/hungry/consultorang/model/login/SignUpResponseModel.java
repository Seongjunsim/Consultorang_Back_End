package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper = false)
public class SignUpResponseModel extends ParentModel {
    int userId;
    String email;
    String businessName;
    String pw;
    String token;
}

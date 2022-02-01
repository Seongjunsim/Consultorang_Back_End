package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class SignInResponseModel extends ParentModel {
    int userId;
    String email;
    String businessName;
    String pw;
    String token;
}

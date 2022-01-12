package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SignInResponseModel extends ParentModel {
    int userId;
    String userEmail;
    String userName;
    String token;
}

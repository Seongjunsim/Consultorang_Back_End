package com.hungry.consultorang.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungry.consultorang.model.ParentModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserModel extends ParentModel {
    int userId;
    String id;
    String pw;
    String name;
}

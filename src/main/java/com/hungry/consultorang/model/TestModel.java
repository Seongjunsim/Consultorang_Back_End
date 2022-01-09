package com.hungry.consultorang.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungry.consultorang.model.ParentModel;
import lombok.*;

@Data
public class TestModel extends ParentModel {
    int id;
    String nameVal;
}

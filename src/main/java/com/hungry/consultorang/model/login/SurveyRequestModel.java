package com.hungry.consultorang.model.login;

import com.hungry.consultorang.model.ParentModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SurveyRequestModel extends ParentModel {
    private String businessType;
    private String businessIngre;
    private String businessCookway;
    private String businessAlcohol;
    private String businessAlready;
    private Integer businessStaff;
    private String businessHours;
}

package com.hungry.consultorang.service.test;

import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.TestModel;

import java.util.List;


public interface TestService {
    public TestModel getUser();
    public List<ParentModel> getUserList();
}

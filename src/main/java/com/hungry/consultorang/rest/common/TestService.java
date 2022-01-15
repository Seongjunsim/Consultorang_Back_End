package com.hungry.consultorang.rest.common;

import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.TestModel;

import java.util.List;


public interface TestService {
    public TestModel getUser();
    public List<Object> getUserList();
}

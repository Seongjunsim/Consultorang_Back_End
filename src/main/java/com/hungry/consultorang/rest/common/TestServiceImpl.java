package com.hungry.consultorang.rest.common;

import com.hungry.consultorang.common.dao.CommonDao;
import com.hungry.consultorang.model.ParentModel;
import com.hungry.consultorang.model.TestModel;
import com.hungry.consultorang.rest.common.TestService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    CommonDao commonDao;

    public TestServiceImpl(CommonDao commonDao) {
        this.commonDao = commonDao;
    }

    @Override
    public TestModel getUser() {
        return null;
    }

    @Override
    public List<Object> getUserList() {
        return commonDao.selectList("common.getUserList");
    }
}

package com.hungry.consultorang.common.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommonDao {

    private SqlSession sqlSession;

    public CommonDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<HashMap<String, Object>> getUser(){
        return sqlSession.selectList("common.getUserList");
    }


}

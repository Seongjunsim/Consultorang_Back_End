package com.hungry.consultorang.common.dao;

import com.hungry.consultorang.model.ParentModel;
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

    public Object selectOne(String statement, ParentModel param){
        return sqlSession.selectOne(statement, param);
    }

    public List<ParentModel> selectList(String statement){
        return sqlSession.selectList(statement);
    }

    public List<ParentModel> selectList(String statement, ParentModel param){
        return sqlSession.selectList(statement, param);
    }

    public int update(String statement, ParentModel param){
        return sqlSession.update(statement, param);
    }

    public int insert(String statement, ParentModel param){
        return sqlSession.insert(statement, param);
    }

    public int delete(String statement, ParentModel param){
        return sqlSession.delete(statement, param);
    }


}

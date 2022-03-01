package com.hungry.consultorang.common.dao;

import com.hungry.consultorang.model.ParentModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class CommonDao {

    private SqlSession sqlSession;
    private SqlSession batchSqlSession;

    public CommonDao(@Qualifier("sqlSessionTemplate") SqlSession sqlSession,
                     @Qualifier("batchSqlSessionTemplate") SqlSession batchSqlSession) {
        this.batchSqlSession = batchSqlSession;
        this.sqlSession = sqlSession;
    }

    public Object selectOne(String statement, Object param){
        return sqlSession.selectOne(statement, param);
    }

    public List<Object> selectList(String statement){
        return sqlSession.selectList(statement);
    }

    public List<Object> selectList(String statement, Object param){
        return sqlSession.selectList(statement, param);
    }
    public List<ParentModel> selectModelList(String statement, Object param){
        return sqlSession.selectList(statement, param);
    }

    public int update(String statement, Object param){
        return sqlSession.update(statement, param);
    }

    public int batchUpdate(String statement, Object param){
        return batchSqlSession.update(statement, param);
    }

    public int insert(String statement, Object param){
        return sqlSession.insert(statement, param);
    }

    public int batchInsert(String statement, Object param){
        return batchSqlSession.insert(statement, param);
    }

    public int delete(String statement, Object param){
        return sqlSession.delete(statement, param);
    }

    public void flushStatements(){
        this.batchSqlSession.flushStatements();
    }

}

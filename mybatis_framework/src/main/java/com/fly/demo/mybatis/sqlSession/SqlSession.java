package com.fly.demo.mybatis.sqlSession;

import java.util.List;

public interface SqlSession {


     <T> T sellectOne(String statementId,Object paramObj);

     <T> List<T> sellectList(String statementId, Object paramObj);
}

package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.MappedStatement;

import java.util.List;

/**
 * @ClassName DefaultSqlSession
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:29
 **/
public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <T> T sellectOne(String statementId, Object paramObj) {
        List<Object> list = sellectList(statementId, paramObj);
        if(list!=null && list.size()>0){
            return (T) list.get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> sellectList(String statementId, Object paramObj) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        Executor executor = new CacheExecutor();
        return executor.query(mappedStatement,configuration,paramObj);
    }
}

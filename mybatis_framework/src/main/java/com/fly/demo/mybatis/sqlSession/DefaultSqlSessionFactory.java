package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.Configuration;

/**
 * @ClassName DefaultSqlSessionFactory
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:28
 **/
public class DefaultSqlSessionFactory implements SqlSessionFactory{

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}

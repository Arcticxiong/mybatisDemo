package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.MappedStatement;

import java.util.List;

/**
 * @ClassName Executor
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:35
 **/
public interface Executor {

    <T> List<T> query(MappedStatement mappedStatement, Configuration configuration,Object paramObj);
}

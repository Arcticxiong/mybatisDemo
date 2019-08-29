package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.MappedStatement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CacheExecutor
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:36
 **/
public class CacheExecutor implements Executor{

    private Map<String,List<Object>> oneLevelMap = new HashMap<>();

    private BaseExecutor deligate = new BaseExecutor();

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object paramObj) {
        String sql = mappedStatement.getSqlSource().getBoundSql(paramObj).getSql();
        if(oneLevelMap.containsKey(sql)){
            return (List<T>) oneLevelMap.get(sql);
        }
        List<Object> result = deligate.query(mappedStatement, configuration, paramObj);
        oneLevelMap.put(sql,result);
        return (List<T>) result;
    }
}

package com.fly.demo.mybatis.sqlsource;


import com.fly.demo.mybatis.config.BoundSql;

public interface SqlSource {

	// 该方法返回的BoundSql，其实是一个组合模式
	// BoundSql类中封装的是JDBC可执行的SQL语句，以及该SQL语句对应的参数信息
	// 该方法的调用时机：是SqlSession执行的时候，去调用
	public BoundSql getBoundSql(Object paramObject);

}

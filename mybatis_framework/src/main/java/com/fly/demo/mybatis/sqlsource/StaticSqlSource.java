package com.fly.demo.mybatis.sqlsource;

import com.fly.demo.mybatis.config.BoundSql;
import com.fly.demo.mybatis.config.ParameterMapping;

import java.util.List;


public class StaticSqlSource implements SqlSource {

	private String sql;
	private List<ParameterMapping> parameterMappings;

	public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings) {
		this.sql = sql;
		this.parameterMappings = parameterMappings;
	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		return new BoundSql(sql, parameterMappings,paramObject);
	}

}

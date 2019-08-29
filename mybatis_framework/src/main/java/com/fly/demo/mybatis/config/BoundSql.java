package com.fly.demo.mybatis.config;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {

	private String sql;

	// ParameterMapping就是封装了#{}中的参数信息
	private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

	public Object paramObject;

	public BoundSql(String sql, List<ParameterMapping> parameterMappings, Object paramObject) {
		super();
		this.sql = sql;
		this.parameterMappings = parameterMappings;
		this.paramObject = paramObject;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public List<ParameterMapping> getParameterMappings() {
		return parameterMappings;
	}

	public void addParameterMapping(ParameterMapping parameterMapping) {
		this.parameterMappings.add(parameterMapping);
	}

}

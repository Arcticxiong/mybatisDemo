package com.fly.demo.mybatis.sqlsource;


import com.fly.demo.mybatis.utils.GenericTokenParser;
import com.fly.demo.mybatis.utils.ParameterMappingTokenHandler;

public class SqlSourceParser {

	public SqlSource parse(String originalSql) {
		ParameterMappingTokenHandler handler = new ParameterMappingTokenHandler();
		// 创建分词解析器
		GenericTokenParser parser = new GenericTokenParser("#{", "}", handler);
		// 解析#{}
		String sql = parser.parse(originalSql);
		// 将解析之后的SQL信息，封装到StaticSqlSource对象中
		// SQL字符串是带有?号的字符串，?相关的参数信息，封装到ParameterMapping集合中
		return new StaticSqlSource(sql, handler.getParameterMappings());
	}
}

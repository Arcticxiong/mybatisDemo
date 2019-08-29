package com.fly.demo.mybatis.sqlsource;

import com.fly.demo.mybatis.config.BoundSql;
import com.fly.demo.mybatis.sqlnode.DynamicContext;
import com.fly.demo.mybatis.sqlnode.MixedSqlNode;

public class RawSqlSource implements SqlSource {

	private MixedSqlNode rootSqlNode;

	public RawSqlSource(MixedSqlNode rootSqlNode) {
		this.rootSqlNode = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		// 将带有#{}和${}还有SQL片段或者SQL标签的SQL语句，进行处理
		// 只需要将#{}进行处理
		// 直接使用一个成熟的工具类去进行字符串解析即可。
		//
		// 1.如果是${}还有SQL片段或者SQL标签的SQL语句--->解析结果就是字符串的拼接
		//
		// 2.如果是#{}--->解析结果就是带有?的占位符，并且还有ParameterMapping对象
		DynamicContext dynamicContext = new DynamicContext(paramObject);
		rootSqlNode.apply(dynamicContext);

		SqlSourceParser sourceParser = new SqlSourceParser();
		SqlSource sqlSource = sourceParser.parse(dynamicContext.getSql());
		// 将解析后的SQL语句和对应的参数信息封装到BoundSql对象中
		return sqlSource.getBoundSql(paramObject);
	}

}

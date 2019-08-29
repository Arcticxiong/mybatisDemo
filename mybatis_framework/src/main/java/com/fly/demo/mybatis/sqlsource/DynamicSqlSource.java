package com.fly.demo.mybatis.sqlsource;

import com.fly.demo.mybatis.config.BoundSql;
import com.fly.demo.mybatis.sqlnode.DynamicContext;
import com.fly.demo.mybatis.sqlnode.SqlNode;

public class DynamicSqlSource implements SqlSource {

	private SqlNode rootSqlNode;

	public DynamicSqlSource(SqlNode rootSqlNode) {
		this.rootSqlNode = rootSqlNode;
	}

	@Override
	public BoundSql getBoundSql(Object paramObject) {
		// 解析各个SqlNode节点，然后将解析后的sql文本信息，拼接到DynamicContext中的StringBuilder
		// 此时会解析${}，但是不会解析#{}
		DynamicContext dynamicContext = new DynamicContext(paramObject);
		rootSqlNode.apply(dynamicContext);

		// 将DynamicSqlSource解析为StaticSqlSource
		// 处理#{}
		SqlSourceParser sqlSourceParser = new SqlSourceParser();
		SqlSource sqlSource = sqlSourceParser.parse(dynamicContext.getSql());
		// 此时调用的是staticSqlSource的内容
		return sqlSource.getBoundSql(paramObject);
	}

}

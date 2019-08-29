package com.fly.demo.mybatis.sqlnode;

import java.util.List;

public class MixedSqlNode implements SqlNode {
	
	private List<SqlNode> sqlNodes;
	
	public MixedSqlNode(List<SqlNode> sqlNodes) {
		this.sqlNodes = sqlNodes;
	}

	@Override
	public void apply(DynamicContext context) {
		for (SqlNode sqlNode : sqlNodes) {
			sqlNode.apply(context);
		}
	}

}

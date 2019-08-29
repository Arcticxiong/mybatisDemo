package com.fly.demo.mybatis.sqlnode;

import java.util.HashMap;
import java.util.Map;

public class DynamicContext {

	private StringBuilder sb = new StringBuilder();

	private Map<String, Object> bindings = new HashMap<String, Object>();

	public DynamicContext(Object paramObject) {
		bindings.put("_parameter", paramObject);
	}

	public Map<String, Object> getBindings() {
		return bindings;
	}

	public void appendSql(String sql) {
		sb.append(sql);
		sb.append(" ");
	}

	public String getSql() {
		return sb.toString();
	}
}

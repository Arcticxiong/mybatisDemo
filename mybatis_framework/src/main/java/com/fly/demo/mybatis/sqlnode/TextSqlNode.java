package com.fly.demo.mybatis.sqlnode;

import com.fly.demo.mybatis.utils.GenericTokenParser;
import com.fly.demo.mybatis.utils.OgnlUtils;
import com.fly.demo.mybatis.utils.SimpleTypeRegistry;
import com.fly.demo.mybatis.utils.TokenHandler;

public class TextSqlNode implements SqlNode {

	private String sqlText;

	public TextSqlNode(String sqlText) {
		super();
		this.sqlText = sqlText;
	}

	@Override
	public void apply(DynamicContext context) {
		// 表示sqlText中包含${}
		// 比如说${username}
		TokenHandler handler = new BindingTokenParser(context);
		GenericTokenParser parser = new GenericTokenParser("${", "}", handler);
		//parser.parse(sqlText)解析之后的值就是${username}被替换的值
		context.appendSql(parser.parse(sqlText));
	}

	public boolean isDynamic() {
		if (sqlText.indexOf("${") > -1) {
			return true;
		}
		return false;
	}

	private static class BindingTokenParser implements TokenHandler {
		private DynamicContext context;

		public BindingTokenParser(DynamicContext context) {
			this.context = context;
		}

		@Override
		public String handleToken(String content) {
			Object paramObject = context.getBindings().get("_parameter");
			if (paramObject == null) {
				context.getBindings().put("value", null);
			} else if (SimpleTypeRegistry.isSimpleType(paramObject.getClass())) {
				context.getBindings().put("value", paramObject);
			}

			Object value = OgnlUtils.getValue(content, context.getBindings().get("_parameter"));
			String srtValue = value == null ? "" : String.valueOf(value);
			return srtValue;
		}

	}
}

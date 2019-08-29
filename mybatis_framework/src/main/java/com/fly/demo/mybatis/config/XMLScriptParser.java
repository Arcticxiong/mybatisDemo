package com.fly.demo.mybatis.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fly.demo.mybatis.sqlnode.*;
import com.fly.demo.mybatis.sqlsource.DynamicSqlSource;
import com.fly.demo.mybatis.sqlsource.RawSqlSource;
import com.fly.demo.mybatis.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;


public class XMLScriptParser {
	private Element selectElement;

	private boolean isDynamic = false;

	private final Map<String, NodeHandler> nodeHandlerMap = new HashMap<>();

	public XMLScriptParser(Element selectElement) {
		this.selectElement = selectElement;
		// 初始化动态SQL中的节点处理器集合
		initNodeHandlerMap();
	}

	private void initNodeHandlerMap() {
		nodeHandlerMap.put("if", new IfHandler());
		// nodeHandlerMap.put("where", new WhereHandler());
		// nodeHandlerMap.put("foreach", new ForeachHandler());
	}

	public SqlSource parseScriptNode() {
		// 先解析各个节点
		MixedSqlNode rootSqlNode = parseDynamicTags(selectElement);
		SqlSource sqlSource;
		if (isDynamic) {
			// 封装${}和动态标签的sql信息
			// 如果既带有#{}，又带有${}的话，也封装到该对象中
			sqlSource = new DynamicSqlSource(rootSqlNode);

		} else {
			// 封装带有#{}的sql信息
			sqlSource = new RawSqlSource(rootSqlNode);
		}
		return sqlSource;
	}

	private MixedSqlNode parseDynamicTags(Element selectElement) {
		List<SqlNode> sqlNodes = new ArrayList<SqlNode>();

		for (int i = 0, size = selectElement.nodeCount(); i < size; i++) {
			Node node = selectElement.node(i);
			// 文本节点
			if (node instanceof Text) {
				String text = node.getText().trim();
				if (text == null || text.equals("")) {
					continue;
				}
				TextSqlNode textSqlNode = new TextSqlNode(text);
				// 如果sql文本中包含了${}，那么就封装为TextSqlNode
				// 否则的话，只包含#{}或者直接可以jdbc执行的sql文本封装到StaticTextSqlNode
				if (textSqlNode.isDynamic()) {
					sqlNodes.add(textSqlNode);
					isDynamic = true;
				} else {
					sqlNodes.add(new StaticTextSqlNode(text));
				}
			} else if (node instanceof Element) { // 子标签
				Element element = (Element) node;
				String nodeName = node.getName().toLowerCase();

				// 在加载sql节点信息的时候，就需要调用
				NodeHandler nodeHandler = nodeHandlerMap.get(nodeName);
				nodeHandler.handleNode(element, sqlNodes);
				isDynamic = true;
			}
		}
		return new MixedSqlNode(sqlNodes);
	}

	private class IfHandler implements NodeHandler {

		@Override
		public void handleNode(Element nodeToHandle, List<SqlNode> targetContents) {
			// 对if标签进行解析
			MixedSqlNode rootSqlNode = parseDynamicTags(nodeToHandle);

			String test = nodeToHandle.attributeValue("test");
			IfSqlNode ifSqlNode = new IfSqlNode(test, rootSqlNode);
			targetContents.add(ifSqlNode);
		}
	}
}

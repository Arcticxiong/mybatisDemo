package com.fly.demo.mybatis.config;

import com.fly.demo.mybatis.sqlnode.SqlNode;
import org.dom4j.Element;

import java.util.List;

public interface NodeHandler {
	public void handleNode(Element nodeToHandle, List<SqlNode> targetContents);
}

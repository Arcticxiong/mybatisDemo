package com.fly.demo.mybatis.config;

import com.fly.demo.mybatis.sqlsource.SqlSource;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.List;

/**
 * @ClassName XMLMapperParser
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:03
 **/
public class XMLMapperParser {

    private Configuration configuration;

    private String namespace;

    public XMLMapperParser(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(Element rootElement) {
        namespace = rootElement.attributeValue("namespace");
        List<Element> elements = rootElement.elements("select");
        for (Element selectElement : elements) {
            parseStatement(selectElement);
        }
    }

    private void parseStatement(Element selectElement) {
        String id = selectElement.attributeValue("id");
        String parameterType = selectElement.attributeValue("parameterType");
        Class<?> parameterTypeClass = resolveClass(parameterType);
        String resultType = selectElement.attributeValue("resultType");
        Class<?> resultTypeClass = resolveClass(resultType);
        String statementType = selectElement.attributeValue("statementType");

        // SqlSource就是封装了SQL语句
        // 此时封装的SQL语句是没有进行处理的，什么时候处理呢？
        // 处理时机，就是在SqlSession执行的时候
        // SELECT * FROM user WHERE id = #{id}
//        String sqlText = selectElement.getTextTrim();
//        SqlSource sqlSource = new SqlSource(sqlText);
        SqlSource sqlSource = createSqlSource(selectElement);
        String statementId = namespace+"."+id;
        MappedStatement mappedStatement = new MappedStatement(statementId, parameterTypeClass, resultTypeClass, statementType, sqlSource);
        configuration.setMappedStatement(statementId,mappedStatement);
    }

    private SqlSource createSqlSource(Element selectElement) {
        XMLScriptParser scriptParser = new XMLScriptParser(selectElement);
        // 解析select标签的子标签，包括文本标签和其他子标签
        return scriptParser.parseScriptNode();
    }

    private Class<?> resolveClass(String classpath) {
        try {
            return Class.forName(classpath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}

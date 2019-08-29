package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.XMLConfigParser;
import com.fly.demo.mybatis.utils.DocumentUtils;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * 使用构建者模式去创建SqlSessionFactory
 * @ClassName SqlSessionFactoryBuilder
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 16:02
 **/
public class SqlSessionFactoryBuilder {


    public SqlSessionFactory build(InputStream inputStream) {
        Document document = DocumentUtils.readDocument(inputStream);
        XMLConfigParser xmlConfigParser = new XMLConfigParser();
        Configuration configuration = xmlConfigParser.parse(document.getRootElement());
        return build(configuration);
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }
}

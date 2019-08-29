package com.fly.demo.mybatis.config;

import com.fly.demo.mybatis.utils.DocumentUtils;
import com.fly.demo.mybatis.utils.Resources;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigParser {

    private Configuration configuration;

    public XMLConfigParser() {
        this.configuration = new Configuration();
    }


    /**
     * @param rootElement 全局配置文件的根标签<configuration>
     * @return
     */
    public Configuration parse(Element rootElement) {
        parseEnvironments(rootElement.element("environments"));
        parseMappers(rootElement.element("mappers"));
        return configuration;
    }

    private void parseMappers(Element element) {
        List<Element> elements = element.elements("mapper");
        for (Element mapperElement : elements) {
            parseMapper(mapperElement);
        }
    }

    private void parseMapper(Element mapperElement) {
        String resource = mapperElement.attributeValue("resource");
        InputStream inputStream = Resources.getResourceAsStream(resource);
        Document document = DocumentUtils.readDocument(inputStream);
        XMLMapperParser xmlMapperParser = new XMLMapperParser(configuration);
        xmlMapperParser.parse(document.getRootElement());
    }

    /**
     * @param element <environments>
     */
    private void parseEnvironments(Element element) {
        String defaultEnvId = element.attributeValue("default");
        List<Element> envElements = element.elements();
        for (Element envElement : envElements) {
            String envId = envElement.attributeValue("id");
            if (envId == null || envId.equals("")) {
                break;
            }
            if (envId.equals(defaultEnvId)) {
                parseDateSource(envElement.element("dataSource"));
            }
        }

    }

    /**
     * @param element <dataSource>
     */
    private void parseDateSource(Element element) {
        String type = element.attributeValue("type");
        if (type.equals("DBCP")) {
            Properties properties = new Properties();
            List<Element> elements = element.elements("property");
            for (Element proElement : elements) {
                String name = proElement.attributeValue("name");
                String value = proElement.attributeValue("value");
                properties.put(name, value);
            }
            BasicDataSource dataSource = new BasicDataSource();
            dataSource.setDriverClassName(properties.getProperty("driver"));
            dataSource.setUrl(properties.getProperty("url"));
            dataSource.setUsername(properties.getProperty("username"));
            dataSource.setPassword(properties.getProperty("password"));
            configuration.setDataSource(dataSource);
        }
    }
}

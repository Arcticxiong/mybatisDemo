package com.fly.demo.test;

import java.io.InputStream;

import com.fly.demo.dao.UserDao;
import com.fly.demo.dao.UserDaoImpl;
import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.XMLConfigParser;
import com.fly.demo.mybatis.sqlSession.SqlSessionFactory;
import com.fly.demo.mybatis.sqlSession.SqlSessionFactoryBuilder;
import com.fly.demo.mybatis.utils.DocumentUtils;
import com.fly.demo.mybatis.utils.Resources;
import com.fly.demo.po.User;
import org.dom4j.Document;
import org.junit.Test;


public class UserDaoTest {

    @Test
    public void testInitConfiguration() throws Exception {

        // 1. 指定全局配置文件的路徑
        String resource = "SqlMapConfig.xml";
        // 2. 读取全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // 3. dom解析
        Document document = DocumentUtils.readDocument(inputStream);
        // 4. 使用mybatis的标签语义去将document中的信息解析到Configuration对象中
        XMLConfigParser configParser = new XMLConfigParser();
        Configuration configuration = configParser.parse(document.getRootElement());
        System.out.println(configuration);
    }

	@Test
	public void testQueryUserById() {
		UserDao userDao = new UserDaoImpl();
		User param = new User();
		param.setId(1);
		User user = userDao.queryUserById(param);
		System.out.println(user);

	}

	@Test
	public void testQueryUserById2() {
        // 1. 指定全局配置文件的路徑
        String resource = "SqlMapConfig.xml";
        // 2. 读取全局配置文件
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        UserDao userDao = new UserDaoImpl(sqlSessionFactory);
        User param = new User();
        param.setId(1);
        param.setUsername("张三");
        User user = userDao.queryUserById2(param);
        System.out.println(user);
	}

}

package com.fly.demo.mybatis.sqlSession;

import com.fly.demo.mybatis.config.BoundSql;
import com.fly.demo.mybatis.config.Configuration;
import com.fly.demo.mybatis.config.MappedStatement;
import com.fly.demo.mybatis.config.ParameterMapping;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseExecutor
 * @Description TODO
 * @Author xiongfei
 * @Date 2019/8/27 15:39
 **/
public class BaseExecutor implements Executor {

    @Override
    public <T> List<T> query(MappedStatement mappedStatement, Configuration configuration, Object paramObj) {
        List<Object> results = null;
        try {
            //1.获取连接
            Connection connection = getConnection(configuration);
            //2.获取sql
            BoundSql boundSql = mappedStatement.getSqlSource().getBoundSql(paramObj);
            String sql = boundSql.getSql();
            //3.创建statement对象
            String statementType = mappedStatement.getStatementType();
            statementType = statementType == null || "".equals(statementType) ? "prepared" : statementType;
            if ("prepared".equals(statementType)) {
                PreparedStatement preparedStatement = createStatement(connection, sql);
                paramize(mappedStatement, preparedStatement, paramObj, boundSql);
                ResultSet resultSet = preparedStatement.executeQuery();
                results = handleResult(resultSet,mappedStatement.getResultTypeClass());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (List<T>) results;
    }

    private List<Object> handleResult(ResultSet resultSet, Class<?> resultTypeClass) throws Exception {
        List<Object> results = new ArrayList<>();
        while (resultSet.next()){
            Object newInstance = resultTypeClass.newInstance();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i+1);
                Field field = newInstance.getClass().getDeclaredField(columnName);
                field.setAccessible(true);
                field.set(newInstance,resultSet.getObject(i+1));
            }
            results.add(newInstance);
        }
        return results;
    }

    private void paramize(MappedStatement mappedStatement, PreparedStatement preparedStatement, Object paramObj, BoundSql boundSql) {
        try {
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            Class<?> parameterTypeClass = mappedStatement.getParameterTypeClass();
            if (parameterTypeClass == Integer.class) {
                preparedStatement.setInt(1, (int) paramObj);
            }else {
                //POJO类型
                for (int i = 0; i < parameterMappings.size(); i++) {
                    ParameterMapping parameterMapping = parameterMappings.get(i);
                    String name = parameterMapping.getName();
                    Field field = parameterTypeClass.getDeclaredField(name);
                    field.setAccessible(true);
                    preparedStatement.setObject(i+1,field.get(paramObj));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private PreparedStatement createStatement(Connection connection, String sql) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Connection getConnection(Configuration configuration) {
        DataSource dataSource = configuration.getDataSource();
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

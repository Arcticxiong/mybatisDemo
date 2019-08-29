package com.fly.demo.mybatis.config;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    private DataSource dataSource;

    private Map<String,MappedStatement> mappedStatement = new HashMap<>();

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatement.get(statementId);
    }

    public void setMappedStatement(String statementId,MappedStatement mappedStatement) {
        this.mappedStatement.put(statementId,mappedStatement);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}

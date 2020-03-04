package com.huawei.mybatis.mapper.config;

import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author xixi
 * @Description： 自定义数据库类型识别器
 * @create 2020/3/3
 * @since 1.0.0
 */
public class MyDatabaseIdProvider implements DatabaseIdProvider {

    private static final String DATABASE_MYSQL = "MySQL";
    private static final String DATABASE_POSTGRESQL = "PostgreSQL";
    private static final String DATABASE_ORACLE = "Oracle";
    private static final String DATABASE_DB2 = "DB2";

    @Override
    public void setProperties(Properties p) {
        System.out.println(p.getProperty("Oracle"));
    }

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        Connection conn = dataSource.getConnection();
        String dbName = conn.getMetaData().getDatabaseProductName();
        String dbAlias = "";
        switch (dbName) {
            case DATABASE_MYSQL:
                dbAlias = "mysql";
                break;
            case DATABASE_POSTGRESQL:
                dbAlias = "pg";
                break;
            case DATABASE_ORACLE:
                dbAlias = "oracle";
                break;
            case DATABASE_DB2:
                dbAlias = "db2";
                break;
            default:
                break;
        }
        return dbAlias;
    }


}

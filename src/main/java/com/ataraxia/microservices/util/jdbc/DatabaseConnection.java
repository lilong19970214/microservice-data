package com.ataraxia.microservices.util.jdbc;

//import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author LiLong
 */
public final class DatabaseConnection {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    /**
     * 获取数据库链接 [阿里数据源的方式]
     */
//    public static Connection getConnection(DruidDataSource dataSource) throws SQLException {
//        return getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
//    }

    /**
     * 获取数据库连接 [spring jdbc的配置]
     */
    public static Connection getConnection(DataSourceProperties dataSource) throws SQLException {
        return getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
    }

    /**
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param url      数据库链接地址
     */
    public static Connection getConnection(String url, String username, String password) throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}

package com.ataraxia.microservices.util;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author LiLong
 */
public final class SqlExecuteUtil {

    private final Logger logger = LoggerFactory.getLogger(SqlExecuteUtil.class);

    private ScriptRunner runner;
    private static Connection connection;

    /**
     * 依然单例
     */
    private SqlExecuteUtil() {

    }

    /**
     * 获取数据库链接 [阿里数据源的方式]
     */
    public static SqlExecuteUtil getConnection(DruidDataSource dataSource) throws SQLException {
        return getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
    }

    /**
     * 获取数据库连接 [spring jdbc的配置]
     */
    public static SqlExecuteUtil getConnection(DataSourceProperties dataSource) throws SQLException {
        return getConnection(dataSource.getUrl(), dataSource.getUsername(), dataSource.getPassword());
    }

    /**
     * @param username 数据库用户名
     * @param password 数据库密码
     * @param url      数据库链接地址
     */
    public static SqlExecuteUtil getConnection(String url, String username, String password) throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        return new SqlExecuteUtil();
    }

    /**
     * <p>可以是表创建，增删改字段或者持久化操作。但是最好不要在此处做持久化操作。还是去手写mapper文件比较合理 <p/>
     *
     * @param sqlScript sql脚本内容
     * @author lilong
     * @date 2021年5月31日
     */
    public boolean mybatisExec(String sqlScript) {
        Reader reader = new StringReader(sqlScript);
        return this.mybatisExec(reader);
    }

    /**
     * sql脚本批处理
     */
    public boolean mybatisExec(String... sqlScript) {
        Reader[] readers = new Reader[sqlScript.length];
        for (int i = 0; i < sqlScript.length; i++) {
            readers[i] = new StringReader(sqlScript[i]);
        }
        return this.mybatisExec(readers);
    }

    /**
     * SQL脚本文件批处理
     */
    public boolean mybatisExec(File... files) {
        Reader[] readers = new Reader[files.length];
        for (int i = 0; i < files.length; i++) {
            if (files[i].exists()) {
                try {
                    readers[i] = new FileReader(files[i]);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
        return this.mybatisExec(readers);
    }

    private boolean mybatisExec(Reader... reader) {
        try {
            //这里的链接获取后就交由Mybatis ScriptRunner管理状态，因此不需要单独关闭状态之类的，也不建议单独关闭链接
            runner = new ScriptRunner(connection);
            //自动提交会在sql提交后自动执行，遇到错误以及异常会捕获然后进行自动回滚。是比较安全的一种执行策略
            runner.setAutoCommit(true);
            runner.setStopOnError(true);
            //批处理，可以执行一条也可执行多条
            for (Reader value : reader) {
                runner.runScript(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("SQL执行失败，错误原因{}", e.getMessage());
            return false;
        } finally {
            Objects.requireNonNull(runner).closeConnection();
        }
        return true;
    }
}

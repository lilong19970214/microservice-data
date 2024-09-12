package com.ataraxia.microservices.util.jdbc;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

/**
 * @author LiLong
 *
 * <p>执行sql脚本。构造时获取指定的数据库链接即可！
 *    同理ibatis还提供了一个SqlRunner执行SQL语句的查询以及持久化操作。其构造与当前工具类也是一样的。
 * <p/>
 */
public final class SqlExecuteUtil {

    private final Logger logger = LoggerFactory.getLogger(SqlExecuteUtil.class);

    private ScriptRunner runner;
    private final Connection connection;

    /**
     * 依然单例
     */
    public SqlExecuteUtil(Connection connection) {
        this.connection = connection;
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


    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection("jdbc:postgresql://127.0.0.1:5432/microservice","postgres","123456");
        new SqlExecuteUtil(connection).mybatisExec("select * from public.sys_user;");
    }
}

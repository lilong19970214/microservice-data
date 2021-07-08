package com.ataraxia.microservices.controller;

import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.entity.GenConfig;
import com.ataraxia.microservices.util.generate.CodeGenerateUtils;
import com.ataraxia.microservices.util.jdbc.DatabaseInfoUtils;
import com.ataraxia.microservices.util.jdbc.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author MyPC
 */
public class TestController {

    public static void main(String[] args) throws SQLException {
        GenConfig genConfig = new GenConfig("cwsj_qj_qrcode", "qrcode_info");
        Connection connection = DatabaseConnection.getConnection("jdbc:postgresql://192.168.100.100:5430/qj", "dcsm", "dcsm8523", "org.postgresql.Driver");
        DatabaseInfoUtils databaseInfoUtils = new DatabaseInfoUtils(connection);
        DatabaseTableInfo tableInfo = databaseInfoUtils.getTables(genConfig);
        List<DatabaseColumnInfo> columnInfoList = databaseInfoUtils.getColumns(genConfig);
        CodeGenerateUtils.generate(tableInfo, columnInfoList);
        connection.close();
    }
}

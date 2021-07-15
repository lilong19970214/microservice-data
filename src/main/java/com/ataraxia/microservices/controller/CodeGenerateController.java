package com.ataraxia.microservices.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.entity.GenConfig;
import com.ataraxia.microservices.util.generate.CodeGenerateUtils;
import com.ataraxia.microservices.util.jdbc.DatabaseInfoUtils;
import com.ataraxia.microservices.util.jdbc.DatabaseConnection;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lilong
 */
@RestController
public class CodeGenerateController {

    @Resource
    private DruidDataSource dataSource;
    @Resource
    private CodeGenerateUtils codeGenerateUtils;


    @GetMapping("/generate")
    public void generate(GenConfig genConfig, HttpServletResponse response) throws SQLException {
        //获取数据库连接
        Connection connection = DatabaseConnection.getConnection(dataSource);
        //获取指定库的表信息 和字段信息
        DatabaseInfoUtils databaseInfoUtils = new DatabaseInfoUtils(connection);
        DatabaseTableInfo tableInfo = databaseInfoUtils.getTables(genConfig);
        List<DatabaseColumnInfo> columnInfoList = databaseInfoUtils.getColumns(genConfig);
        //生成操作 关闭链接
        codeGenerateUtils.generate(tableInfo, columnInfoList, response);
    }


    @GetMapping("/info")
    public Principal info(Authentication authentication) {
        return authentication;
    }
}

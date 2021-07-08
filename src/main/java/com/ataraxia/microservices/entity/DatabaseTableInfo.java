package com.ataraxia.microservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>数据库表信息<p/>
 *
 * @author lilong
 */
@Data
@AllArgsConstructor
public class DatabaseTableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

}

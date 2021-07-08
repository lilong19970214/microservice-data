package com.ataraxia.microservices.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lilong
 *
 * <p>代码生成时连接数据库时信息<p/>
 */
@Getter
@AllArgsConstructor
public class GenConfig {

    /**
     * 数据库名
     */
    private final String catalog;

    /**
     * 表明
     */
    private final String tableName;
}

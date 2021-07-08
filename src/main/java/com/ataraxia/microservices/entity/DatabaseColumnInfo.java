package com.ataraxia.microservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lilong
 *
 * <P>表字段信息<P/>
 */
@Data
@AllArgsConstructor
public class DatabaseColumnInfo {

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段注释
     */
    private String columnComment;

    /**
     * 可否为空【生成的时候用不上，只是记录一下】
     */
    private Boolean nullAble;

    /**
     * 类型长度 【生成的时候用不上，只是记录一下】
     */
    private Integer typeSize;
}

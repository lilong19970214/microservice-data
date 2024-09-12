package com.ataraxia.microservices.util.jdbc;

import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.entity.GenConfig;
import com.ataraxia.microservices.enums.DatabaseEnum;
import com.ataraxia.microservices.util.conversion.ConversionDataTypeUtils;
import com.google.common.base.CaseFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author lilong
 * <p>数据库表信息获取工具类 【支持大范围拓展】 <p/>
 */
public class DatabaseInfoUtils {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);

    private final Connection connection;

    public DatabaseInfoUtils(Connection connection) {
        this.connection = connection;
    }

    private DatabaseMetaData getMetaData() throws SQLException {
        return connection.getMetaData();
    }

    /**
     * <b>获取当前数据库中的表信息<b/>
     *
     * @param genConfig 数据库信息
     */
    public DatabaseTableInfo getTables(GenConfig genConfig) {
        DatabaseTableInfo databaseTableInfo = null;
        ResultSet resultSet = null;
        try {
            resultSet = getMetaData().getTables(genConfig.getCatalog(), null,
                    "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                //TODO 具体决定要取什么值参照 ResultSet 各个数据库驱动对应的实现类中有常量 【pgsql的实现类为 PgDatabaseMetaData】
                String tableName = resultSet.getString("TABLE_NAME");
                if (tableName.equals(genConfig.getTableName())) {
                    //表注释
                    String tableComment = resultSet.getString("REMARKS");
                    databaseTableInfo = new DatabaseTableInfo(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName), tableComment);
                }
            }
        } catch (Exception e) {
            logger.error("Please check your database conf! {}", e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(resultSet).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return databaseTableInfo;
    }

    /**
     * <b>获取当前数据库指定表的所有字段信息<b/>
     *
     * @param genConfig 数据库信息
     */
    public List<DatabaseColumnInfo> getColumns(GenConfig genConfig) {
        List<DatabaseColumnInfo> list = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            resultSet = getMetaData().getColumns(genConfig.getCatalog(), null, genConfig.getTableName(), "%");
            while (resultSet.next()) {
                //字段名
                String columnName = resultSet.getString(DatabaseEnum.COLUMN_NAME.toString());
                //字段信息
                String remark = resultSet.getString(DatabaseEnum.REMARKS.toString());
                //是否为空
                Boolean nullAble = resultSet.getInt(DatabaseEnum.NULLABLE.toString()) == 1;
                //字段长度
                Integer typeSize = resultSet.getInt(DatabaseEnum.COLUMN_SIZE.toString());
                //字段类型
                String typeName = resultSet.getString(DatabaseEnum.TYPE_NAME.toString());

                list.add(new DatabaseColumnInfo(CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, columnName),
                        ConversionDataTypeUtils.findByJdbcType(typeName),
                        remark,
                        nullAble,
                        typeSize));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                Objects.requireNonNull(resultSet).close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


}

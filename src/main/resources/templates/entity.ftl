<#--生成实体类的模板-->

public class ${tableInfo.tableName} implements Serializable {
    <#list columnInfoList as column>

       /**
        * ${column.columnComment}
        */
        private ${column.columnType} ${column.columnName}
    </#list>

}

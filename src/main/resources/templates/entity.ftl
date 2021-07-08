<#--生成实体类的模板-->
package com.nice.code

/**
 * @description ${tableInfo.tableComment}
 * @author Li Long
 */
public class ${tableInfo.tableName} implements Serializable {

    <#--生成字段模板-->
    <#list columnInfoList as column>
       /**
        * ${column.columnComment}
        */
        private ${column.columnType} ${column.columnName}

    </#list>

    <#--生成get set 方法-->
    <#list columnInfoList as column>

        public ${column.columnType} get${column.columnName}() {
            return ${column.columnName}
        }

        public void ${"set" + column.columnName + "()"} {
            return this.${column.columnName} = ${column.columnName}
        }
    </#list>


}

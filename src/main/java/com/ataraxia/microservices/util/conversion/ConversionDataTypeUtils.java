package com.ataraxia.microservices.util.conversion;

import com.ataraxia.microservices.util.conversion.service.ConversionType;

import java.util.*;

/**
 * @author MyPC
 */
public class ConversionDataTypeUtils {

    /**
     * 根据数据库字段类型查找Java对应的数据类型
     *
     * @param jdbcType 数据库字段类型
     */
    public static String findByJdbcType(final String jdbcType) {
        Map<String, String> map = new HashMap<>(16);
        ServiceLoader<ConversionType> serviceLoader = ServiceLoader.load(ConversionType.class);
        for (ConversionType conversionType : serviceLoader) {
            map = conversionType.conversion();
            break;
        }
        return map.get(jdbcType) == null ? "String" : map.get(jdbcType);
    }

}

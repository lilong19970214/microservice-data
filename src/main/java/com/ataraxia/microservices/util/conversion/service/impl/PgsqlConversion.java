package com.ataraxia.microservices.util.conversion.service.impl;

import com.ataraxia.microservices.util.conversion.service.ConversionType;

import java.util.HashMap;
import java.util.Map;

/**
 * postgresql 数据库类型转换
 *
 * @author lilong
 */
public class PgsqlConversion implements ConversionType {

    private static final Map<String, String> CONVERSION = new HashMap<>(16);

    static {
        CONVERSION.put("varchar", "String");
        CONVERSION.put("char", "String");
        CONVERSION.put("text", "String");
        CONVERSION.put("int8", "Long");
        CONVERSION.put("int4", "Integer");
        CONVERSION.put("int2", "Integer");
        CONVERSION.put("float4", "Float");
        CONVERSION.put("float8", "Double");
        CONVERSION.put("date", "Date");
        CONVERSION.put("timestamp", "Date");
    }

    @Override
    public Map<String, String> conversion() {
        return CONVERSION;
    }
}

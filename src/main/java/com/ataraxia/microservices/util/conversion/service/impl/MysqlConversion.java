package com.ataraxia.microservices.util.conversion.service.impl;

import com.ataraxia.microservices.util.conversion.service.ConversionType;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>mysql 数据库<b/>
 *
 * @author lilong
 */
public class MysqlConversion implements ConversionType {

    private static final Map<String, String> CONVERSION = new HashMap<>(16);

    static {
        CONVERSION.put("int2", "Integer");
    }

    @Override
    public Map<String, String> conversion() {
        return CONVERSION;
    }
}

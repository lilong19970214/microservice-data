package com.ataraxia.microservices.util.conversion.service;

import java.util.List;
import java.util.Map;

/**
 * 这里暂时用SPI实现，如果后期使用web模块，可以使用service注解来注册单例bean
 *
 * @author lilong
 */
public interface ConversionType {

    /**
     * @Return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @Author Li Long
     * @Description
     * @Date 14:53 2021/7/8
     * @Params []
     */
    Map<String, String> conversion();
}

package com.ataraxia.microservices.util.generate;

import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.util.MapUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>代码生成工具类<p/>
 *
 * @author lilong
 */
public class CodeGenerateUtils {

    private static final Configuration CONFIGURATION = new Configuration(Configuration.VERSION_2_3_31);

    public static void generate(DatabaseTableInfo tableInfo, List<DatabaseColumnInfo> columnInfoList) {
        CONFIGURATION.setClassForTemplateLoading(CodeGenerateUtils.class, "/templates");
        try {
            Template entityTemplate = CONFIGURATION.getTemplate("entity.ftl");
            Map<String, Object> data = MapUtils.of("tableInfo", tableInfo, "columnInfoList", columnInfoList);
            entityTemplate.process(data, new FileWriter("D:/123.java"));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}

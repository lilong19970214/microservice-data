package com.ataraxia.microservices.util.generate;

import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.util.MapUtils;
import com.ataraxia.microservices.util.ZipUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>代码生成工具类<p/>
 *
 * @author lilong
 */
@Component
public class CodeGenerateUtils {

    @Resource
    private FreeMarkerConfigurer configurer;


    public void generate(DatabaseTableInfo tableInfo, List<DatabaseColumnInfo> columnInfoList, HttpServletResponse response) {
        try {
            //freemarker的默认配置就是在resources下面的templates，是可以修改的。也可以动态获取到
            Template entityTemplate = configurer.getConfiguration().getTemplate("entity.ftl");
            Map<String, Object> data = MapUtils.of("tableInfo", tableInfo, "columnInfoList", columnInfoList);
            StringWriter writer = new StringWriter();
            entityTemplate.process(data, writer);
            ZipUtils.compressFileOutput(MapUtils.of("entity.java", writer), "生成代码.zip", response);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}

package com.ataraxia.microservices.util.generate;

import com.ataraxia.microservices.entity.DatabaseColumnInfo;
import com.ataraxia.microservices.entity.DatabaseTableInfo;
import com.ataraxia.microservices.util.MapUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.FileWriter;
import java.io.IOException;
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


    public void generate(DatabaseTableInfo tableInfo, List<DatabaseColumnInfo> columnInfoList) {
        try {
            //freemarker的默认配置就是在resources下面的templates，是可以修改的。也可以动态获取到
            Template entityTemplate = configurer.getConfiguration().getTemplate("entity.ftl");
            Map<String, Object> data = MapUtils.of("tableInfo", tableInfo, "columnInfoList", columnInfoList);
            entityTemplate.process(data, new FileWriter("D:/123.java"));
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

}

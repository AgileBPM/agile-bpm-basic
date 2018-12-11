package com.dstz.sys.freemark;

import com.dstz.base.core.util.PropertyUtil;
import com.dstz.sys.api.freemark.IFreemarkEngine;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;

@Component
public class FreemarkEngine implements IFreemarkEngine {
    // 配置来自app-resources.xml
    private Configuration formTemplateConfig;


    public Configuration getFormTemplateConfiguration() throws IOException {
        if (formTemplateConfig == null) {
            String templatePath = PropertyUtil.getProperty("formTemplateUrl", "src/main/resources/templates");
            formTemplateConfig = new Configuration();
            formTemplateConfig.setDefaultEncoding("UTF-8");
            formTemplateConfig.setDirectoryForTemplateLoading(new File(templatePath));
        }
        return formTemplateConfig;
    }


    @Override
    public String genFormByTemplateName(String templateName, Object model) throws IOException, TemplateException {
        Template template = getFormTemplateConfiguration().getTemplate(templateName);

        StringWriter writer = new StringWriter();
        template.process(model, writer);
        return writer.toString();
    }

    @Override
    public String parseByString(String templateSource, Object model)
            throws TemplateException, IOException {
        Configuration cfg = new Configuration();
        StringTemplateLoader loader = new StringTemplateLoader();
        cfg.setTemplateLoader(loader);
        cfg.setClassicCompatible(true);
        loader.putTemplate("freemaker", templateSource);
        Template template = cfg.getTemplate("freemaker");
        StringWriter writer = new StringWriter();
        template.process(model, writer);
        return writer.toString();
    }

}

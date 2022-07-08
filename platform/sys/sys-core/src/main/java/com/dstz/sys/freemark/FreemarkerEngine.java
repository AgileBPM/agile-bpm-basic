package com.dstz.sys.freemark;

import java.io.File;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.core.util.PropertyUtil;
import com.dstz.sys.api.freemark.IFreemarkerEngine;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Component
public class FreemarkerEngine implements IFreemarkerEngine {
	// 配置来自app-resources.xml
	private Configuration formTemplateConfig;
	protected Logger LOG = LoggerFactory.getLogger(getClass());

	public Configuration getFormTemplateConfiguration() {
		try {
			if (formTemplateConfig == null) {
				String templatePath = PropertyUtil.getProperty("formTemplateUrl", "src/main/resources/templates");
				formTemplateConfig = new Configuration();
				formTemplateConfig.setDefaultEncoding("UTF-8");
				formTemplateConfig.setDirectoryForTemplateLoading(new File(templatePath));
			}
			return formTemplateConfig;
		} catch (Exception e) {
			throw new BusinessError(e);
		}

	}

	@Override
	public String genFormByTemplateName(String templateName, Object model) {
		try {
			Template template = getFormTemplateConfiguration().getTemplate(templateName);

			StringWriter writer = new StringWriter();
			template.process(model, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new BusinessError(e);
		}

	}

	@Override
	public String parseByString(String templateSource, Object model) {
		try {
			Configuration cfg = new Configuration();
			StringTemplateLoader loader = new StringTemplateLoader();
			cfg.setTemplateLoader(loader);
			cfg.setClassicCompatible(true);
			loader.putTemplate("freemaker", templateSource);
			Template template = cfg.getTemplate("freemaker");
			StringWriter writer = new StringWriter();
			template.process(model, writer);
			return writer.toString();
		} catch (Exception e) {
			LOG.error(String.format("freemaker模板【%s】解析失败：%s", templateSource, e.getMessage()));
			throw new BusinessError(String.format("freemaker模板【%s】解析失败", templateSource), e);
		}
	}

}

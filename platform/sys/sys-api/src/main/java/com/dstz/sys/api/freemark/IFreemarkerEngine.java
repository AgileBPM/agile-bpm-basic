package com.dstz.sys.api.freemark;

import java.io.IOException;

import freemarker.template.TemplateException;

public interface IFreemarkerEngine {

    /**
     * 把指定的模板生成对应的字符串。
     *
     * @param templateName 模板名，模板的基础路径为：WEB-INF/template目录。
     * @param model        传入数据对象。
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String genFormByTemplateName(String templateName, Object model);

    /**
     * 根据字符串模版解析出内容
     *
     * @param templateSource 字符串模版。
     * @param model          环境参数。
     * @return 解析后的文本
     * @throws TemplateException
     * @throws IOException
     */
    public String parseByString(String templateSource, Object model);

}
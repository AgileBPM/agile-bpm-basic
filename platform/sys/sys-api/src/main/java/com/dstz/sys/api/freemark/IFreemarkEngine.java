package com.dstz.sys.api.freemark;

import freemarker.template.TemplateException;

import java.io.IOException;

public interface IFreemarkEngine {

    /**
     * 把指定的模板生成对应的字符串。
     *
     * @param templateName 模板名，模板的基础路径为：WEB-INF/template目录。
     * @param model        传入数据对象。
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public String genFormByTemplateName(String templateName, Object model) throws IOException, TemplateException;

    /**
     * 根据字符串模版解析出内容
     *
     * @param templateSource 字符串模版。
     * @param model          环境参数。
     * @return 解析后的文本
     * @throws TemplateException
     * @throws IOException
     */
    public String parseByString(String templateSource, Object model) throws TemplateException, IOException;

}
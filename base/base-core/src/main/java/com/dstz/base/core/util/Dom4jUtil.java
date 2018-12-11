package com.dstz.base.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.*;
import org.xml.sax.SAXException;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * xml操作类。<br>
 * 包括xml的读取，xml的转换等。
 * @author dstz
 */
public class Dom4jUtil {
    private static final Log logger = LogFactory.getLog(Dom4jUtil.class);

    /**
     * 将符合格式的xml字符串 转化成 Document
     *
     * @param s
     * @return
     */
    public static Document loadXml(String s) {
        Document document = null;
        try {
            document = DocumentHelper.parseText(s);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage(), ex.getCause());
        }
        return document;
    }

    /**
     * 加载一个XML文件转成Document对象
     *
     * @param filename
     * @return
     */
    public static Document load(String filename, String encode) {
        Document document = null;
        try {
            SAXReader saxReader = new SAXReader();
            saxReader.setEncoding(encode);
            document = saxReader.read(new File(filename));
        } catch (Exception ex) {
            // logger.error("load XML File error:"+ex.getMessage());
        }
        return document;
    }

    /**
     * 按指定编码转化字符串为Document
     *
     * @param xml
     * @param encode
     * @return
     * @throws UnsupportedEncodingException
     */
    public static Document loadXml(String xml, String encode)
            throws UnsupportedEncodingException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                xml.getBytes(encode));
        return loadXml(inputStream, encode);
    }

    /**
     * 根据输入流返回Document
     *
     * @param is
     * @return
     */
    public static Document loadXml(InputStream is) {
        return loadXml(is, "utf-8");
    }

    public static Document loadXml(InputStream is, String charset) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            reader.setEncoding(charset);
            document = reader.read(is);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    /**
     * 将DOM写入到文件
     *
     * @param document
     * @param fileName
     * @throws IOException
     */
    public static void write(Document document, String fileName)
            throws IOException {
        String xml = document.asXML();
        FileUtil.writeFile(fileName, xml);
    }

    /**
     * 将XML写入文件
     *
     * @param str
     * @param fileName
     * @throws IOException
     * @throws DocumentException
     */
    public static void write(String str, String fileName) throws IOException,
            DocumentException {
        Document document = DocumentHelper.parseText(str);
        write(document, fileName);
    }

    /**
     * 根据URL取得DOM
     *
     * @param url
     * @return
     * @throws DocumentException
     */
    public Document load(URL url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(url);
        return document;
    }

    /**
     * 载入一个xml文档
     *
     * @param filename
     * @return 成功返回Document对象，失败返回null
     */
    public static Document load(String filename) {
        Document document = null;
        try {
            SAXReader reader = new SAXReader();
            document = reader.read(new File(filename));
            document.normalize();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return document;
    }

    public static Document loadByClassPath(String filePath) throws IOException,
            DocumentException {
        return Dom4jUtil.loadByClassPath(filePath, "utf-8", null);
    }

    public static Document loadByClassPath(String filePath, String charset)
            throws IOException, DocumentException {
        return Dom4jUtil.loadByClassPath(filePath, charset, null);
    }

    public static Document loadByClassPath(String filePath, String charset,
                                           ClassLoader classLoader) throws IOException, DocumentException {

        InputStream is = null;
        Document document = null;
        URL url = null;

        if (classLoader != null) {
            url = classLoader.getResource("/" + filePath);
        } else {
            url = Dom4jUtil.class.getResource("/" + filePath);
        }

        try {
            is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            SAXReader reader = new SAXReader();
            reader.setEncoding(charset);
            document = reader.read(isr);
            document.normalize();
            return document;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * 根据xsl转换xmldom.
     *
     * @param document
     * @param stylesheet
     * @return
     * @throws Exception
     */
    public static String transFormXsl(String xml, String xsl,
                                      Map<String, String> map) throws Exception {

        StringReader xmlReader = new StringReader(xml);
        StringReader xslReader = new StringReader(xsl);
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");

        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(
                xslReader));
        if (map != null) {
            // 添加参数
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> obj = it.next();
                transformer.setParameter(obj.getKey(), obj.getValue());
            }
        }
        StreamSource xmlSource = new StreamSource(xmlReader);

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        transformer.transform(xmlSource, result);

        return writer.toString();
    }

    public static String transXmlByXslt(String xml, String xslPath,
                                        Map<String, String> map) throws Exception {
        Document document = loadXml(xml);
        document.setXMLEncoding("UTF-8");

        Document result = styleDocument(document, xslPath, map);

        return docToString(result);
    }

    public static String transXmlByXslt(String xml, InputStream styleStream,
                                        Map<String, String> map) throws Exception {
        Document document = loadXml(xml);
        document.setXMLEncoding("UTF-8");

        Document result = styleDocument(document, styleStream, map);

        return docToString(result);
    }

    public static String transFileXmlByXslt(String xmlPath, String xslPath,
                                            Map<String, String> map) throws Exception {
        Document document = load(xmlPath);
        document.setXMLEncoding("UTF-8");

        Document result = styleDocument(document, xslPath, map);

        return docToString(result);
    }

    /**
     * 把Document对象转成XML String
     *
     * @param document
     * @return
     */
    public static String docToString(Document document) {
        String s = "";
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            OutputFormat format = new OutputFormat("  ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            logger.error("docToString error:" + ex.getMessage());
        }
        return s;
    }

    /**
     * document转为xml字符串(带xml缩进格式)
     *
     * @param document
     * @return
     */
    public static String docToPrettyString(Document document) {
        String s = "";
        try {
            Writer writer = new StringWriter();
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setSuppressDeclaration(true); // 去除 <?xml version="1.0"
            // encoding="UTF-8"?>
            XMLWriter xmlWriter = new XMLWriter(writer, format);
            xmlWriter.write(document);
            s = writer.toString();
        } catch (Exception ex) {
            logger.error("docToString error:" + ex.getMessage());
        }
        return s;
    }

    /**
     * 将xml和样式表转成bpmn20xml。
     *
     * @param document
     * @param stylesheet
     * @param map
     * @return
     * @throws Exception
     */
    public static Document styleDocument(Document document, String stylesheet,
                                         Map<String, String> map) throws Exception {
        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
        // load the transformer using JAXP
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(
                stylesheet));
        if (map != null) {
            // 添加参数
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> obj = it.next();
                transformer.setParameter(obj.getKey(), obj.getValue());
            }
        }
        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }

    public static Document styleDocument(Document document, InputStream stylesheetStream,
                                         Map<String, String> map) throws Exception {
        // load the transformer using JAXP

        System.setProperty("javax.xml.transform.TransformerFactory", "org.apache.xalan.processor.TransformerFactoryImpl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(stylesheetStream));
        if (map != null) {
            // 添加参数
            Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> obj = it.next();
                transformer.setParameter(obj.getKey(), obj.getValue());
            }
        }
        // now lets style the given document
        DocumentSource source = new DocumentSource(document);
        DocumentResult result = new DocumentResult();
        transformer.transform(source, result);

        // return the transformed document
        Document transformedDoc = result.getDocument();
        return transformedDoc;
    }

    public static boolean validateXMLSchema(String xml, File... xsdFiles) {
        return validateXMLSchema(new ByteArrayInputStream(xml.getBytes()),
                xsdFiles);
    }

    public static boolean validateXMLSchema(InputStream xmlIs, File... xsdFiles) {
        try {

            SchemaFactory factory = SchemaFactory
                    .newInstance("http://www.w3.org/2001/XMLSchema");

            List<Source> sourceList = new ArrayList<Source>();

            for (File file : xsdFiles) {
                sourceList.add(new StreamSource(file));
            }

            Source[] sources = sourceList.toArray(new Source[]{});
            Schema schema = factory.newSchema(sources);

            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlIs));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (SAXException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取element元素中的属性
     *
     * @param element
     * @param attrName 属性名称
     * @return
     */
    public static String getString(Element element, String attrName) {
        return getString(element, attrName, false);
    }

    /**
     * 获取element元素中的属性
     *
     * @param element
     * @param attrName 属性名称
     * @param fuzzy    是否添加模糊匹配的符号
     * @return
     */
    public static String getString(Element element, String attrName,
                                   Boolean fuzzy) {
        if (element == null)
            return null;
        String val = element.attributeValue(attrName);
        if (StringUtils.isEmpty(val))
            return null;
        if (fuzzy) {
            val = "%" + val + "%";
        }
        return val;
    }

}

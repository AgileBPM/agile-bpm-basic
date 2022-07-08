package com.dstz.base.rest.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.constant.StringConstants;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.FieldLogic;
import com.dstz.base.api.query.FieldRelation;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.query.DefaultFieldLogic;
import com.dstz.base.db.model.query.DefaultQueryField;
import com.dstz.base.db.model.query.DefaultQueryFilter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;

public class RequestUtil {
	 protected static final Logger LOGGER = LoggerFactory.getLogger(AppUtil.class);
	/**
	 * 从Request中获取业务必填的字段
	 * @param request
	 * @param key 
	 * @param errorMsg 业务异常提示 (这里抛出的异常最好有 @cathErr 注解捕获并包装错误json 返回前端)
	 * @return
	 */
	public static String getRQString(HttpServletRequest request, String key, String errorMsg) {
		String result = RequestUtil.getString(request, key,null);
		if(result == null) {
			throw new BusinessMessage(String.format("[%s] %s",key, errorMsg),BaseStatusCode.PARAM_ILLEGAL);
		}
		return result;
    }
	

	public static String getRQString(HttpServletRequest request, String key) {
		return getRQString(request, key, key);
	}
	
	
    /**
     * 取字符串类型的参数。 如果取得的值为null，则返回默认字符串。
     *
     * @param request
     * @param key          字段名
     * @param defaultValue
     * @return
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue, boolean b) {
        String value = request.getParameter(key);
        if (StringUtil.isEmpty(value))
            return defaultValue;
        if (b) {
            return value.replace("'", "''").trim();
        } else {
            return value.trim();
        }
    }

    /**
     * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
     *
     * @param request
     * @param key     字段名
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {
        return getString(request, key, "", true);
    }

    /**
     * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(HttpServletRequest request, String key, boolean b) {
        return getString(request, key, "", b);
    }

    /**
     * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。
     *
     * @param request
     * @param key
     * @param b       是否替换'为''
     * @return
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue) {
        return getString(request, key, defaultValue, true);
    }


    /**
     * 从Request中取得指定的小写值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static String getLowercaseString(HttpServletRequest request, String key) {
        return getString(request, key).toLowerCase();
    }

    /**
     * 从request中取得int值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static int getInt(HttpServletRequest request, String key) {
        return getInt(request, key, 0);
    }

    /**
     * 从request中取得int值,如果无值则返回缺省值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static int getInt(HttpServletRequest request, String key, int defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return defaultValue;
        return Integer.parseInt(str);

    }



    /**
     * 根据一串逗号分隔的字符串取得字符串形数组
     *
     * @param request
     * @param key
     * @return
     */
    public static String[] getStringAryByStr(HttpServletRequest request, String key) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))  return null;
        
        String[] arrayStr = str.split(",");
        List<String> strList = new ArrayList<>(arrayStr.length);
        //过滤掉空字符集
        for(String s: arrayStr) {
        	if(StringUtil.isNotEmpty(s)) {
        		strList.add(s);
        	}
        }
       return  ArrayUtil.toArray(strList, String.class);
    }

 
    /**
     * 从Request中取得boolean值,如无值则返回缺省值 false, 如值为数字1，则返回true
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key) {
        return getBoolean(request, key, false);
    }

    /**
     * 从Request中取得boolean值 对字符串,如无值则返回缺省值, 如值为数字1，则返回true
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key, boolean defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str)) {
        	return defaultValue;
        }
        if (StringUtils.isNumeric(str))
            return (Integer.parseInt(str) == 1 ? true : false);
        return Boolean.parseBoolean(str);
    }


    /**
     * 从Request中取得Date值,如无值则返回缺省值,如有值则返回 yyyy-MM-dd HH:mm:ss 格式的日期,或者自定义格式的日期
     *
     * @param request
     * @param key
     * @param style
     * @return
     * @throws ParseException
     */
    public static Date getDate(HttpServletRequest request, String key, String style) throws ParseException {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return null;
        if (StringUtil.isEmpty(style))
            style = "yyyy-MM-dd HH:mm:ss";
        return (Date) DateUtil.parse(str, style);
    }

    /**
     * 从Request中取得Date值,如无值则返回缺省值null, 如果有值则返回 yyyy-MM-dd 格式的日期
     *
     * @param request
     * @param key
     * @return
     * @throws ParseException
     */
    public static Date getDate(HttpServletRequest request, String key) throws ParseException {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return null;
        return (Date) DateUtil.parseDate(str);

    }

    /**
     * 从Request中取得Date值,如无值则返回缺省值 如有值则返回 yyyy-MM-dd HH:mm:ss 格式的日期
     *
     * @param request
     * @param key
     * @return
     * @throws ParseException
     */
    public static Date getTimestamp(HttpServletRequest request, String key) throws ParseException {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return null;
        return (Date) DateUtil.parseDateTime(str);
    }


    /**
     * 处理页面进来的请求参数。
     *
     * <pre>
     * 	1.参数字段命名规则。
     * 	a:参数名称^参数类型+条件 eg：a^VEQ 则表示，a字段是varchar类型，条件是eq ^后第一个参数为数据类型
     * 	b:参数名字^参数类型  eg：b^V则表示，b字段是varchar类型 用于sql拼参数
     * 	2.在这里构建的逻辑都是and逻辑。
     * 3.参数类型:V :字符串 varchar N:数字number D:日期date
     * 条件参数 枚举：QueryOP
     *
     * </pre>
     *
     * @param request
     * @param queryFilter
     */
    public static void handleRequestParam(HttpServletRequest request, DefaultQueryFilter queryFilter) {
        FieldLogic andFieldLogic = new DefaultFieldLogic(FieldRelation.AND);
        Enumeration<String> paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String key = paramNames.nextElement();
            String value = request.getParameter(key);
            handleKeyVal(key, value, queryFilter, andFieldLogic);
        }
        
        Enumeration<String> attrNames = request.getAttributeNames();
        while (attrNames.hasMoreElements()) {
            String key = attrNames.nextElement();
            Object val = request.getAttribute(key);
            handleKeyVal(key, val, queryFilter, andFieldLogic);
        }
        queryFilter.setFieldLogic(andFieldLogic);
    }
    
    private static void handleKeyVal(String key,Object val,DefaultQueryFilter queryFilter,FieldLogic andFieldLogic) {
    	//老版本用的^但是Tomcat8 get请求不支持此类型参数。故这里做兼容使用-
        String specialSplitKey = "$";
        if (key.contains("^")) {
        	specialSplitKey = "^";
        }
        
        if (!key.contains(specialSplitKey)) {
            return;
        }
       
        if (val == null) return;
		
        String value =val.toString();
        value = value.trim();
		if (value.equals(StringConstants.EMPTY)) return;

        String[] aryParamKey = key.split("\\"+specialSplitKey);
        if (aryParamKey.length != 2) {
            return;
        }

        String columnName = aryParamKey[0];//截取字段名字
        String condition = aryParamKey[1];//截取条件
        String columnType = condition.substring(0, 1);

        if ("V".equals(columnType)) {
            columnType = ColumnType.VARCHAR.getKey();
        } else if ("N".equals(columnType)) {
            columnType = ColumnType.NUMBER.getKey();
        } else if ("D".equals(columnType)) {
            columnType = ColumnType.DATE.getKey();
        }
        //仅仅将参数设置进入mapper环境
        if (condition.length() == 1) {
            queryFilter.addParamsFilter(columnName, BeanUtils.getValue(columnType,value));
        } else {
            QueryOP queryOP = QueryOP.getByVal(condition.substring(1, condition.length()));
            andFieldLogic.getWhereClauses().add(new DefaultQueryField(columnName, queryOP, BeanUtils.getValue(columnType, queryOP, value)));
        }
    }
    

    /**
     * 把当前上下文的请求封装在map中
     * @deprecated
     * @param request
     * @param keepArray 保持为数组
     * @return
     */
    public static Map getParameterValueMap(HttpServletRequest request, boolean keepArray) {
    	Map<String,Object> map = new HashMap();
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String paramKey = String.valueOf(params.nextElement());
            String[] values = request.getParameterValues(paramKey);
            if (ArrayUtil.isEmpty(values)) continue;
             
            String rtn = getByAry(values);
            if (rtn.length() > 0 && values.length > 0) {
                if (keepArray) {
                	map.put(paramKey, rtn.split(","));
                }
                else {
                	map.put(paramKey, rtn);
                }
            }
        }
        return map;
    }

    /**
     * @param aryTmp
     * @param isSecure
     * @return
     */
    private static String getByAry(String[] aryTmp) {
    	if(ArrayUtil.isEmpty(aryTmp))return "";
    	
    	if(aryTmp.length == 1) {
    		return aryTmp[0];
    	}
    	
        StringBuilder rtn = new StringBuilder();
        for (int i = 0; i < aryTmp.length; i++) {
            String str = aryTmp[i].trim();
            
            if(StringUtil.isEmpty(str)) continue;
            rtn.append(str);
            rtn.append(",");
        }
         
        return  rtn.substring(0, rtn .length() - 1);
    }

    /**
     * 根据参数名称获取参数值。
     *
     * <pre>
     * 1.根据参数名称取得参数值的数组。
     * 2.将数组使用逗号分隔字符串。
     * </pre>
     *
     * @param request
     * @param paramName
     * @return
     */
    public static String getStringValues(HttpServletRequest request, String paramName) {
    	String[] aryValue = request.getParameterValues(paramName);
        if (ArrayUtil.isEmpty(aryValue)) return "";
        
        StringBuilder str  = new StringBuilder();
        for (String v : aryValue) {
            if (StringUtil.isEmpty(v)) continue; 
            str.append(v).append(",");
        }
        return str.substring(0, str.length()-1);
    }

   
    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
    	String ip = request.getHeader("x-forwarded-for"); 
    	LOGGER.debug("x-forwarded-for ip: {}" , ip);
    	
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {  
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if( ip.indexOf(",")!=-1 ){
                ip = ip.split(",")[0];
            }
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
            
            LOGGER.debug("Proxy-Client-IP ip: {}" , ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
            LOGGER.debug("WL-Proxy-Client-IP ip: {}" ,ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
            LOGGER.debug("HTTP_CLIENT_IP ip: {}", ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
            LOGGER.debug("HTTP_X_FORWARDED_FOR ip: {}", ip);
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("X-Real-IP");  
            LOGGER.debug("X-Real-IP ip: {}" , ip);
        } 
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
            LOGGER.debug("getRemoteAddr ip: {}", ip);
        }
        return ip;  
    }

    
    /**
     * 下载文件。
     * // path是指欲下载的文件的路径。
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
	    File file = new File(path);
    	downLoadFile(response,file);
	}
    /**
     * 下载文件。
     */
    public static void downLoadFile( HttpServletResponse response, File file) throws IOException {
	 try {
         // 取得文件名。
         String filename = file.getName();
         // 取得文件的后缀名。
         String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

         // 以流的形式下载文件。
         InputStream fis = new BufferedInputStream(new FileInputStream(file));
         byte[] buffer = new byte[fis.available()];
         fis.read(buffer);
         fis.close();
         // 清空response
         response.reset();
         // 设置response的Header
         response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
         response.addHeader("Content-Length", "" + file.length());
         OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
         response.setContentType("application/octet-stream");
         toClient.write(buffer);
         toClient.flush();
         toClient.close();
     } catch (IOException ex) {
         ex.printStackTrace();
     }
    }

}

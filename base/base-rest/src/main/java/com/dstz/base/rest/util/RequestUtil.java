package com.dstz.base.rest.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.constant.ColumnType;
import com.dstz.base.api.exception.BusinessMessage;
import com.dstz.base.api.query.FieldLogic;
import com.dstz.base.api.query.FieldRelation;
import com.dstz.base.api.query.QueryOP;
import com.dstz.base.core.encrypt.Base64;
import com.dstz.base.core.util.BeanCopierUtils;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.model.query.DefaultFieldLogic;
import com.dstz.base.db.model.query.DefaultQueryField;
import com.dstz.base.db.model.query.DefaultQueryFilter;

import cn.hutool.core.date.DateUtil;

public class RequestUtil {
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
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringAry(HttpServletRequest request, String key) {
        String[] aryValue = request.getParameterValues(key);
        if (aryValue == null || aryValue.length == 0) {
            return "";
        }
        String tmp = "";
        for (String v : aryValue) {
            if ("".equals(tmp)) {
                tmp += v;
            } else {
                tmp += "," + v;
            }
        }
        return tmp;
    }

    /**
     * 取得安全字符串。
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getSecureString(HttpServletRequest request, String key, String defaultValue) {
        String value = request.getParameter(key);
        if (StringUtil.isEmpty(value))
            return defaultValue;
        return filterInject(value);

    }

    /**
     * 取得安全字符串，防止程序sql注入，脚本攻击。
     *
     * @param request
     * @param key
     * @return
     */
    public static String getSecureString(HttpServletRequest request, String key) {
        return getSecureString(request, key, "");
    }

    /**
     * 过滤script|iframe|\\||;|exec|insert|select|delete|update|count|chr|truncate |char字符串 防止SQL注入
     *
     * @param str
     * @return
     */
    public static String filterInject(String str) {
        String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
        Pattern regex = Pattern.compile(injectstr, Pattern.CANON_EQ | Pattern.DOTALL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        Matcher matcher = regex.matcher(str);
        str = matcher.replaceAll("");
        str = str.replace("'", "''");
        str = str.replace("-", "—");
        str = str.replace("(", "（");
        str = str.replace(")", "）");
        str = str.replace("%", "％");

        return str;
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
     * 从Request中取得long值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static long getLong(HttpServletRequest request, String key) {
        return getLong(request, key, 0);
    }

    /**
     * 取得长整形数组
     *
     * @param request
     * @param key
     * @return
     */
    public static Long[] getLongAry(HttpServletRequest request, String key) {
        String[] aryKey = request.getParameterValues(key);
        if (BeanUtils.isEmpty(aryKey))
            return null;
        Long[] aryLong = new Long[aryKey.length];
        for (int i = 0; i < aryKey.length; i++) {
            aryLong[i] = Long.parseLong(aryKey[i]);
        }
        return aryLong;
    }

    /**
     * 根据一串逗号分隔的长整型字符串取得长整形数组
     *
     * @param request
     * @param key
     * @return
     */
    public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return null;
        str = str.replace("[", "");
        str = str.replace("]", "");
        String[] aryId = str.split(",");
        Long[] lAryId = new Long[aryId.length];
        for (int i = 0; i < aryId.length; i++) {
            lAryId[i] = Long.parseLong(aryId[i]);
        }
        return lAryId;
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
        if (StringUtil.isEmpty(str))
            return null;
        String[] aryId = str.split(",");
        String[] lAryId = new String[aryId.length];
        for (int i = 0; i < aryId.length; i++) {
            lAryId[i] = (aryId[i]);
        }
        return lAryId;
    }

    /**
     * 根据键值取得整形数组
     *
     * @param request
     * @param key
     * @return
     */
    public static Integer[] getIntAry(HttpServletRequest request, String key) {
        String[] aryKey = request.getParameterValues(key);
        if (BeanUtils.isEmpty(aryKey))
            return null;
        Integer[] aryInt = new Integer[aryKey.length];
        for (int i = 0; i < aryKey.length; i++) {
            aryInt[i] = Integer.parseInt(aryKey[i]);
        }
        return aryInt;
    }

    public static Float[] getFloatAry(HttpServletRequest request, String key) {
        String[] aryKey = request.getParameterValues(key);
        if (BeanUtils.isEmpty(aryKey))
            return null;
        Float[] fAryId = new Float[aryKey.length];
        for (int i = 0; i < aryKey.length; i++) {
            fAryId[i] = Float.parseFloat(aryKey[i]);
        }
        return fAryId;
    }

    /**
     * 从Request中取得long值,如果无值则返回缺省值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static long getLong(HttpServletRequest request, String key, long defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return defaultValue;
        return Long.parseLong(str.trim());
    }

    /**
     * 从Request中取得long值,如果无值则返回缺省值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static Long getLong(HttpServletRequest request, String key, Long defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return defaultValue;
        return Long.parseLong(str.trim());
    }

    /**
     * 从Request中取得float值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static float getFloat(HttpServletRequest request, String key) {
        return getFloat(request, key, 0);
    }

    /**
     * 从Request中取得float值,如无值则返回缺省值
     *
     * @param request
     * @param key
     * @return
     * @throws Exception
     */
    public static float getFloat(HttpServletRequest request, String key, float defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return defaultValue;
        return Float.parseFloat(request.getParameter(key));
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
        if (StringUtil.isEmpty(str))
            return defaultValue;
        if (StringUtils.isNumeric(str))
            return (Integer.parseInt(str) == 1 ? true : false);
        return Boolean.parseBoolean(str);
    }

    /**
     * 从Request中取得boolean值,如无值则返回缺省值 0
     *
     * @param request
     * @param key
     * @return
     */
    public static Short getShort(HttpServletRequest request, String key) {
        return getShort(request, key, (short) 0);
    }

    /**
     * 从Request中取得Short值 对字符串,如无值则返回缺省值
     *
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static Short getShort(HttpServletRequest request, String key, Short defaultValue) {
        String str = request.getParameter(key);
        if (StringUtil.isEmpty(str))
            return defaultValue;
        return Short.parseShort(str);
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
            //老版本用的^但是Tomcat8 get请求不支持此类型参数。故这里做兼容使用-
            String specialSplitKey = "$";
            if (key.contains("^")) {
            	specialSplitKey = "^";
            }
            
            
            if (!key.contains(specialSplitKey)) {
                continue;
            }

            String value = request.getParameter(key);
            if (StringUtil.isEmpty(value)) {
                continue;
            }

            String[] aryParamKey = key.split("\\"+specialSplitKey);
            if (aryParamKey.length != 2) {
                continue;
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
        queryFilter.setFieldLogic(andFieldLogic);
    }
    
    
    public static <T> Class<T> copyProperties(Class<T> source, Object target) {
    	BeanCopierUtils.copyProperties(source, target);
    	return source;
    }

    /**
     * 把当前上下文的请求封装在map中
     *
     * @param request
     * @param remainArray 保持为数组
     * @param isSecure    过滤不安全字符
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Map getParameterValueMap(HttpServletRequest request, boolean remainArray, boolean isSecure) {
        Map map = new HashMap();
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement().toString();
            String[] values = request.getParameterValues(key);
            if (values == null)
                continue;
            if (values.length == 1) {
                String tmpValue = values[0];
                if (tmpValue == null)
                    continue;
                tmpValue = tmpValue.trim();
                if (tmpValue.equals(""))
                    continue;
                if (isSecure)
                    tmpValue = filterInject(tmpValue);
                if (tmpValue.equals(""))
                    continue;
                map.put(key, tmpValue);
            } else {
                String rtn = getByAry(values, isSecure);
                if (rtn.length() > 0) {
                    if (remainArray)
                        map.put(key, rtn.split(","));
                    else
                        map.put(key, rtn);
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
    private static String getByAry(String[] aryTmp, boolean isSecure) {
        String rtn = "";
        for (int i = 0; i < aryTmp.length; i++) {
            String str = aryTmp[i].trim();
            if (!str.equals("")) {
                if (isSecure) {
                    str = filterInject(str);
                    if (!str.equals(""))
                        rtn += str + ",";
                } else {
                    rtn += str + ",";
                }
            }
        }
        if (rtn.length() > 0)
            rtn = rtn.substring(0, rtn.length() - 1);
        return rtn;
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
        String[] values = request.getParameterValues(paramName);
        if (BeanUtils.isEmpty(values))
            return "";
        String tmp = "";
        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                tmp += values[i];
            } else {
                tmp += "," + values[i];
            }
        }
        return tmp;
    }

   
    /**
     * 获取IP地址
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 下载文件。
     *
     * @param response
     * @param b        文件的二进制流
     * @param fileName 文件名称。
     * @throws IOException
     */
    public static void downLoadFileByByte(HttpServletRequest request, HttpServletResponse response, byte[] b, String fileName) throws IOException {
        OutputStream outp = response.getOutputStream();
        if (b.length > 0) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String filedisplay = fileName;
            String agent = (String) request.getHeader("USER-AGENT");
            // firefox
            if (agent != null && agent.indexOf("MSIE") == -1) {
                String enableFileName = "=?UTF-8?B?" + (new String(Base64.getBase64(filedisplay))) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else {
                filedisplay = URLEncoder.encode(filedisplay, "utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            }
            outp.write(b);
        } else {
            outp.write("文件不存在!".getBytes("utf-8"));
        }
        if (outp != null) {
            outp.close();
            outp = null;
            response.flushBuffer();
        }
    }

    /**
     * 下载文件。
     *
     * @param response
     * @param fullPath 文件的全路径
     * @param fileName 文件名称。
     * @throws IOException
     */
    public static void downLoadFile(HttpServletRequest request, HttpServletResponse response, String fullPath, String fileName) throws IOException {
        OutputStream outp = response.getOutputStream();
        File file = new File(fullPath);
        if (file.exists()) {
            response.setContentType("APPLICATION/OCTET-STREAM");
            String filedisplay = fileName;
            String agent = (String) request.getHeader("USER-AGENT");
            // firefox
            if (agent != null && agent.indexOf("MSIE") == -1) {
                String enableFileName = "=?UTF-8?B?" + (new String(Base64.getBase64(filedisplay))) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else {
                filedisplay = URLEncoder.encode(filedisplay, "utf-8");
                response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);
            }
            FileInputStream in = null;
            try {
                outp = response.getOutputStream();
                in = new FileInputStream(fullPath);
                byte[] b = new byte[1024];
                int i = 0;
                while ((i = in.read(b)) > 0) {
                    outp.write(b, 0, i);
                }
                outp.flush();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    in.close();
                    in = null;
                }
                if (outp != null) {
                    outp.close();
                    outp = null;
                    response.flushBuffer();
                }
            }
        } else {
            outp.write("File does not exist!".getBytes("utf-8"));
        }
    }

}

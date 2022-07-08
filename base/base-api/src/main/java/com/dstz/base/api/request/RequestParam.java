package com.dstz.base.api.request;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.dstz.base.api.exception.BusinessException;
import com.dstz.base.api.exception.BusinessMessage;

/**
 * @param params 用于请求参数的存放
 * @说明 系统请求参数
 */
public class RequestParam {
    /**
     * 请求头
     */
    private RequestHead head;
    /**
     * 请求参数
     */
    private Map<String, Object> params = new HashMap<String, Object>();

    /**
     * 添加参数
     *
     * @param key
     * @param val
     */
    public void addParam(String key, Object val) {
        params.put(key, val);
    }

    public Map<String, Object> getParams() {
        return params;
    }


    public void setParams(Map<String, Object> params) {
        this.params = params;
    }


    public Object getParam(String key) {
        return params.get(key);
    }

    public String getStringParam(String key) {
        if (!params.containsKey(key)) return "";
        return (String) params.get(key);
    }

    /**
     * 获取必填的String类型的param
     *
     * @param json
     * @param key
     * @param msg
     * @return
     */
    public String getRQString(String key, String errorMsg) {
        if (this.isParamEmpty(key)) {
            throw new BusinessMessage(errorMsg + "[" + key + "]");
        }
        return (String) params.get(key);
    }

    /**
     * 获取必填的String类型的param
     */
    public Object getRQObject(String key, String errorMsg) {
        if (this.isParamEmpty(key)) {
            throw new BusinessMessage(errorMsg + "[" + key + "]");
        }

        return params.get(key);
    }

    public BigDecimal getBigDecimal(String key) {
        Object val = params.get(key);
        if (val instanceof String) {
            BigDecimal bd = new BigDecimal((String) val);
            return bd;
        }

        return (BigDecimal) params.get(key);
    }

    public Date getRQDate(String key, String errorMsg) {
        if (isParamEmpty(key)) {
            throw new BusinessMessage(errorMsg + "[" + key + "]");
        }

        return getDate(key);
    }

    public Date getDate(String key) {
        Object date = params.get(key);
        if (date instanceof Date) {
            return (Date) date;
        }

        String dateStr = (String) date;
        DateFormat dateFormat;

        if (dateStr.indexOf("/") != -1) {
            dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } else if (dateStr.indexOf("-") != -1) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            return null;
        }

        try {
            Date date1 = null;
            try {
                date1 = dateFormat.parse(dateStr);
            } catch (Exception e) {
                // 如果前端时间格式有略微不匹配、进行损失精度格式化
                date1 = dateFormat.parse(dateStr);
            }
            return date1;
        } catch (Exception e) {
            throw new BusinessException(key + "日期解析出现异常" + e.getMessage());
        }
    }

    public boolean isParamEmpty(String key) {
        if (!params.containsKey(key)) return true;

        Object o = params.get(key);
        if (o == null) {
            return true;
        }

        if (o instanceof String) {
            if (((String) o).trim().length() == 0)
                return true;
        } else if (o instanceof Collection) {
            if (((Collection<?>) o).size() == 0)
                return true;
        } else if (o.getClass().isArray()) {
            if (((Object[]) o).length == 0)
                return true;
        } else if (o instanceof Map) {
            if (((Map<?, ?>) o).size() == 0)
                return true;
        }

        return false;
    }

    public RequestHead getHead() {
        return head;
    }

    public void setHead(RequestHead head) {
        this.head = head;
    }
}

package com.dstz.form.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dstz.base.core.util.BeanUtils;
import com.dstz.base.core.util.JsonUtil;
import com.dstz.base.core.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class FreeMakerUtil {
    // 指令，如果为空则不添加
    public String getAttrs(String attrNames, Object f) {
        StringBuilder sb = new StringBuilder();
        JSONObject field = (JSONObject) JSON.toJSON(f);
        JSONObject option = field.getJSONObject("option");

        String[] attrs = attrNames.split(",");
        for (String attr : attrs) {

            String attrStr = "";
            if ("ht-funcexp".equals(attr)) {
                attrStr = JsonUtil.getString(option, "statFun");
            }
            // 校验
            else if ("ht-validate".equals(attr)) {
                JSONObject validate = field.getJSONObject("validRule");
                if (validate.containsKey("rules")) {
                    JSONArray array = validate.getJSONArray("rules");
                    validate.remove("rules");
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject rule = (JSONObject) array.get(i);
                        validate.put(rule.getString("text"), true);
                    }
                }
                attrStr = validate.toString();

                if (validate.isEmpty()) attrStr = "{}";
            } else if ("selectquery".equals(attr)) {
                attrStr = JsonUtil.getString(option, "jlsz");
            } else if ("ht-date".equals(attr)) {
                attrStr = JsonUtil.getString(option, "dataFormat");
            }
            //number 格式化
            else if ("ht-number".equals(attr)) {
                attrStr = JsonUtil.getString(option, "numberFormat");
                if (StringUtil.isEmpty(attrStr) && "number".equals(field.getString("type")))
                    attrStr = "{}";
            }
            //日期计算
            else if ("ht-datecalc".equals(attr)) {
                attrStr = JsonUtil.getString(option, "datecalc");
            }
            // 编辑器
            else if ("ht-editor".equals(attr)) {
                if (field.containsKey("isEditor") && field.getBoolean("isEditor")) {
                    attrStr = option.toString();
                }
            } else if ("ht-office-plugin".equals(attr)) {
                sb.append(" style='width:" + option.getString("width") + "px;height:" + option.getString("height") + "px' ");

                if (option.containsKey("doctype")) sb.append(" doctype='" + option.getString("doctype") + "'  ");

                sb.append(" ht-office-plugin ");
            }

            // eg: ht-number-format='{formatJson} '
            if (StringUtil.isNotEmpty(attrStr)) {
                sb.append(attr).append("='").append(attrStr).append("' ");
            }
        }
        return sb.toString();
    }

    public String getCtrlDate(Object field) {
        JSONObject tmp = (JSONObject) JSON.toJSON(field);
        JSONObject option = tmp.getJSONObject("option");
        String attrStr = JsonUtil.getString(option, "dataFormat");
        if (StringUtil.isEmpty(attrStr)) {
            return "mobiscroll-date='mobiscroll_setting'";
        }
        //{"dataFormat":"yyyy-MM-dd HH:mm:ss"}
        if ("yyyy-MM-dd HH:mm:ss".equals(attrStr)) {
            return "mobiscroll-datetime='mobiscroll_setting'";
        }

        if ("HH:mm:ss".equals(attrStr)) {
            return "mobiscroll-time='mobiscroll_setting'";
        }

        return "mobiscroll-date='mobiscroll_setting'";
    }


    /**
     * 通过json字符串获取attr属性
     *
     * @param json
     * @param attr
     * @return
     */
    public String getJsonByPath(Object o, String path) {
        JSONObject jsonObject = (JSONObject) JSON.toJSON(o);
        if (jsonObject.isEmpty())
            return "";
        String[] pathList = path.split("\\.");
        if (pathList.length > 1) {
            if (jsonObject.containsKey(pathList[0])) {
                String tempJson = jsonObject.getJSONObject(pathList[0]).toString();
                return getJsonByPath(tempJson, StringUtils.join(ArrayUtils.remove(pathList, 0), "."));
            }
        } else {
            if (jsonObject.containsKey(path)) {
                return jsonObject.getString(path);
            }
        }
        return "";

    }

    public String getSubList(String jsonList, int begin, int end) {
        String[] array = jsonList.split(",");
        String rtn = "";
        for (int i = 0; i < array.length && (i >= begin && i <= end); i++) {
            rtn += array[i] + ",";
        }
        return rtn.substring(0, rtn.length() - 1);
    }


    /**
     * 解析表单字段的option字段
     *
     * @param json
     * @return
     */
    public String getSelectQuery(Object option, Boolean isSub) {
        if (isSub == null) isSub = false;
        if (BeanUtils.isEmpty(option))
            return "{}";
        JSONObject returnObj = new JSONObject();
        JSONObject fromObject = (JSONObject) JSON.toJSON(option);
        JSONObject customQuery = fromObject.getJSONObject("customQuery");
        if (BeanUtils.isNotEmpty(customQuery)) {
            returnObj.put("alias", JsonUtil.getString(customQuery, "alias"));
            returnObj.put("valueBind", JsonUtil.getString(customQuery, "valueBind"));
            returnObj.put("labelBind", JsonUtil.getString(customQuery, "labelBind"));
        }
        JSONArray bindAry = fromObject.getJSONArray("bind");
        JSONObject bindObj = new JSONObject();
        if (bindAry != null)
            for (Object obj : bindAry) {
                JSONObject jobject = (JSONObject) obj;
                JSONObject target = jobject.getJSONObject("json");
                if (BeanUtils.isEmpty(target))
                    continue;
                String key = jobject.getString("field");
                String path = "data.";
                if (isSub) {
                    path = "item.";
                } else {
                    path += target.getString("path");
                    path += ".";
                }

                path += target.getString("name");
                bindObj.put(key, path);
            }
        returnObj.put("bind", bindObj);
        String returnStr = returnObj.toString();
        if (StringUtil.isEmpty(returnStr))
            returnStr = "";
        return returnStr.replaceAll("\"", "'");
    }
}

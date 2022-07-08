package com.dstz.sys.rest.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dstz.base.api.aop.annotion.CatchErr;
import com.dstz.base.api.response.impl.ResultMsg;
import com.dstz.base.core.util.AppUtil;
import com.dstz.base.core.util.ConstantUtil;
import com.dstz.base.core.util.EnumUtil;
import com.dstz.base.core.util.HanyuPinyinHelper;
import com.dstz.base.core.util.StringUtil;
import com.dstz.base.db.api.table.DbType;
import com.dstz.base.db.datasource.DbContextHolder;
import com.dstz.base.rest.ControllerTools;
import com.dstz.base.rest.util.RequestUtil;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;

/**
 * 系统工具类
 *
 * @author aschs
 */
@RestController
@RequestMapping("/sys/tools/")
public class SysToolsController extends ControllerTools {
	/**
	 * <pre>
	 * 根据一个枚举类的路径获取这个枚举的json形式，供前端使用
	 * 注意！！如果枚举在类中间，那么路径如下：com.dstz.base.db.model.Column$TYPE
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getEnum")
	public Object getEnum(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String enumClassPath = RequestUtil.getString(request, "path");
		boolean listMode = RequestUtil.getBoolean(request, "listMode");// 列表模式
		if (listMode) {
			return EnumUtil.toJSONArray(enumClassPath);
		}
		return EnumUtil.toJSON(enumClassPath);
	}

	/**
	 * <pre>
	 * 根据path(类路径)获取key（字段名）的常量
	 * ps:如果key为空，会把path类的全部static final的静态变量获取出来
	 * </pre>
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getConstant")
	public Object getConstant(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classPath = RequestUtil.getString(request, "path");
		String key = RequestUtil.getString(request, "key");
		if (StringUtil.isEmpty(key)) {
			return ConstantUtil.get(classPath);
		}
		return ConstantUtil.get(classPath, key);
	}

	@RequestMapping("getInterFaceImpls")
	@CatchErr
	public Object getInterFaceImpls(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classPath = RequestUtil.getString(request, "classPath");
		Class<?> clazz = Class.forName(classPath);
		Map<String, ?> map = AppUtil.getImplInstance(clazz);
		return map.values();
	}

	@RequestMapping("pinyin")
	public void pinyin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String chinese = RequestUtil.getString(request, "chinese");
		int type = RequestUtil.getInt(request, "type");// 1：全拼 0：首字母拼音
		String result = "";
		if (type == 1) {
			result =  HanyuPinyinHelper.getPinyinString(chinese);
		} else {
			result = HanyuPinyinHelper.getFirstLetters(chinese,HanyuPinyinCaseType.LOWERCASE);
		}
		if(DbType.ORACLE.equalsWithKey(DbContextHolder.getDbType())) {
			result = result.toUpperCase();//oracle默认映射都大写
		}
		response.getWriter().write(result);
	}

    /**
     * 获取当前日期
     *
     * @param format 格式化
     * @return 响应消息
     */
    @CatchErr
    @RequestMapping("getCurrentTime")
    public ResultMsg<String> getCurrentTime(@RequestParam(value = "format") String format) {
        ResultMsg<String> resultMsg = getSuccessResult();
        resultMsg.setData(DateFormatUtils.format(new Date(), format));
        return resultMsg;
    }
     
    @RequestMapping("getResultEnum")
	public Object getResultEnum(@RequestParam String path,@RequestParam(required=false,defaultValue="false") Boolean listMode ) throws Exception {
		if (listMode) {
			return getSuccessResult(EnumUtil.toJSONArray(path));
		}
		
		return getSuccessResult(EnumUtil.toJSON(path));
	}
}

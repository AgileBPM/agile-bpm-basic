package com.dstz.base.core.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.dstz.base.api.constant.BaseStatusCode;
import com.dstz.base.api.exception.BusinessError;
import com.dstz.base.core.jwt.JWTService;

/**
 * <pre>
 * 描述：RestTemplate的ab工具类
 * 作者:aschs
 * 邮箱:aschs@qq.com
 * 日期:2019年7月18日
 * 版权:summer
 * </pre>
 */
public class RestTemplateUtil {
	private static RestTemplate restTemplate;
	private static Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

	private RestTemplateUtil() {

	}

	private static RestTemplate restTemplate() {
		if (restTemplate == null) {
			FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
			List<MediaType> mediaTypes = new ArrayList<>();
			mediaTypes.add(MediaType.valueOf("text/html;charset=UTF-8"));
			mediaTypes.add(MediaType.valueOf("application/json;charset=UTF-8"));
			fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
			fastJsonHttpMessageConverter.setFeatures(SerializerFeature.WriteDateUseDateFormat);
			restTemplate = new RestTemplate();
			restTemplate.getMessageConverters().add(fastJsonHttpMessageConverter);
		}
		return restTemplate;
	}

	/**
	 * <pre>
	 * </pre>
	 * 
	 * @param url
	 * @param data
	 * @param responseType
	 * @return
	 */
	public static <T> T post(String url, Object data, Class<T> responseType) {
		HttpHeaders headers = new HttpHeaders();
		HttpServletRequest request = RequestContext.getHttpServletRequest();
		JWTService jwtService = AppUtil.getBean(JWTService.class);
		String authHeader = request.getHeader(jwtService.getJwtHeader());
		// 支持 session的形式，从cookie中尝试获取
		if (StringUtil.isEmpty(authHeader)) {
			authHeader = CookieUitl.getValueByName(jwtService.getJwtHeader(), request);
			headers.add("Cookie", jwtService.getJwtHeader() + "=" + authHeader);
		} else {
			headers.add(jwtService.getJwtHeader(), authHeader);
		}
		HttpEntity<Object> entity = new HttpEntity<>(data, headers);
		String urlTemp = RequestContext.getUrl(url);
		logger.info("entity: {}", JSON.toJSONString(entity));
		logger.info("url:{}", urlTemp);
		ResponseEntity<T> responseEntity = restTemplate().postForEntity(urlTemp, entity, responseType);
		logger.info("responseEntity:{}", JSON.toJSONString(responseEntity));
		if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			throw new BusinessError(JSON.toJSONString(responseEntity), BaseStatusCode.REMOTE_ERROR);
		}

		return responseEntity.getBody();
	}
	
	public static void main(String[] args) {
		logger.info("123{}","1");
	}

	public static  <T> T post(String url, Object data,ParameterizedTypeReference<T> typeReference) {
		HttpHeaders headers = new HttpHeaders();
		HttpServletRequest request = RequestContext.getHttpServletRequest();
		JWTService jwtService = AppUtil.getBean(JWTService.class);
		String authHeader = request.getHeader(jwtService.getJwtHeader());
		// 支持 session的形式，从cookie中尝试获取
		if (StringUtil.isEmpty(authHeader)) {
			authHeader = CookieUitl.getValueByName(jwtService.getJwtHeader(), request);
			headers.add("Cookie", jwtService.getJwtHeader() + "=" + authHeader);
		} else {
			headers.add(jwtService.getJwtHeader(), authHeader);
		}
		HttpEntity<Object> entity = new HttpEntity<>(data, headers);
		String urlTemp = RequestContext.getUrl(url);
		logger.info("entity: {}", JSON.toJSONString(entity));
		logger.info("url:{}", urlTemp);
		ResponseEntity<T> responseEntity = restTemplate().exchange(url, HttpMethod.POST, entity, typeReference);
		logger.info("responseEntity:{}", JSON.toJSONString(responseEntity));
		if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
			throw new BusinessError(JSON.toJSONString(responseEntity), BaseStatusCode.REMOTE_ERROR);
		}

		return responseEntity.getBody();
	}
}

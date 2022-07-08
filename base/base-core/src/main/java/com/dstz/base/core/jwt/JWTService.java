package com.dstz.base.core.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.dstz.base.core.cache.ICache;
import com.dstz.base.core.util.StringUtil;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt token获取校验
 * 
 * @author jeff
 */
public class JWTService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public static final String JWT_CACHE_REGION = "jwtToken";

	/**
	 * 存放 token 的header
	 */
	@Value("${ab.jwt.header:Authorization}")
	private String header;

	public String getJwtHeader() {
		return header;
	}

	/**
	 * 请求header内的key对应value 的默认开头 Bearer- cookie不支持空格这里做下修改
	 */
	@Value("${ab.jwt.tokenHead:Bearer-}")
	private String tokenHead;

	public String getJwtTokenHead() {
		return tokenHead;
	}

	/*** 是否启用jwt模式默认不开启 **/
	@Value("${ab.jwt.enabled:false}")
	private Boolean enabled;

	public Boolean getJwtEnabled() {
		return enabled;
	}

	/**
	 * 密钥
	 */
	@Value("${ab.jwt.secret:asd%WE^@&fas156dfa}")
	private String secret;

	/**
	 * jwt签发者名称
	 */
	@Value("${ab.jwt.issuer:agileBPM}")
	private String issuer;

	/**
	 * token 有效期，默认一天 
	 */
	@Value("${ab.jwt.expiration: 86400}")
	private Long expirationSecond = 24 * 60 * 60L;

	@Value("${ab.jwt.notBeforeMinute:15}")
	private Long notBeforeMinute;
	
	@Autowired
	private ICache<String> icache;

	
	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token
	 *            令牌
	 * @return 数据声明
	 */
	private Claims getClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 从令牌中获取认证的唯一标识
	 *
	 * @param token
	 *            令牌
	 * @return 用户id
	 */
	public String getSubjectFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			logger.debug("从令牌中获取认证失败", e);
			username = null;
		}
		return username;
	}

	/**
	 * 验证令牌是否时间有效
	 *
	 * @param token
	 *            令牌
	 * @return 是否有效
	 */
	public Boolean validateToken(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			Date notBefore = claims.getNotBefore();
			return new Date().after(notBefore) && new Date().before(expiration);
		} catch (Exception e) {
			logger.debug("验证令牌是否时间有效失败", e);
			return false;
		}
	}
	
	/**
	 * 验证 token是否有效，后端控制token有效期 ，并返回 token的 subject 
	 * @param authToken
	 * @return
	 */
	public String getValidSubjectFromRedisToken(String authToken) {
		if(StringUtil.isEmpty(authToken))return null;
		try {
			Claims claims = getClaimsFromToken(authToken);
			
			if(claims != null) {
				String token = icache.getByKey(JWT_CACHE_REGION, String.format("jwt:%s:%s", claims.getAudience(),claims.getSubject()));
				if(StringUtil.isEmpty(token)) {
					logger.debug("JWT token 校验失败，token 已过期,签发时间 "+DateUtil.formatDateTime(claims.getIssuedAt()));
					return null;
				}
				
				// 有效则放行，并更新剩余时间
				if(authToken.equals(token)){
					icache.add2Region(JWT_CACHE_REGION, String.format("jwt:%s:%s", claims.getAudience(),claims.getSubject()),token);
					return claims.getSubject(); 
				}else {
					logger.info("JWT token 校验失败，服务器 token 与 被校验 token 不一致！ 同一签发对象的 token 不支持多地登录 {}",claims.toString());
				}
				
			}
			
		}catch (Exception e) {
			logger.warn("解析令牌失败", e);
			return null;
		}
		return null;
	}
	

	public void logoutRedisToken(String authToken) {
		if(StringUtil.isEmpty(authToken))return;
		try {
			Claims claims = getClaimsFromToken(authToken);
			
			if(claims != null) {
				icache.delByKey(JWT_CACHE_REGION, String.format("jwt:%s:%s", claims.getAudience(),claims.getSubject()));
			}
		}catch (Exception e) {
			logger.warn("解析令牌失败", e);
		}
	}
	

	/**
	 * 生成Token <br>
	 * 设置token默认有效期 <br>
	 * 同一用户在同一签发对象 只能有一个有效登录
	 * 
	 * @param username 用户账户
	 * @param audience 签发对象( pc ,mobile ,android .....)
	 *            .
	 * @return .
	 */
	public String generateToken(String username,String audience) {
		Assert.notBlank(audience,"生成token 签发对象 不能为空");
		
		String token = Jwts.builder()
				// jwt签发者
				.setIssuer(issuer)
				// jwt所面向的用户
				.setSubject(username)
				// 接收jwt的一方
			    .setAudience(audience)
			//	.setExpiration(new Date(System.currentTimeMillis() + expirationMinute * 60 * 1000))
			//	.setNotBefore(new Date(System.currentTimeMillis() - notBeforeMinute * 60 * 1000))
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
		
		
		// 由缓存负责续期，以及超时判断。且服务端可以注销令牌使用
		if(icache != null) {
			icache.add2Region(JWT_CACHE_REGION, String.format("jwt:%s:%s", audience,username), token);
		}
				
		return token;
	}
	

	public static void main(String[] args) {
		JWTService jwtService = new JWTService();
		jwtService.tokenHead = "Bearer-";
		jwtService.header = "Authorization";
		jwtService.header = "Bearer-";
		jwtService.issuer = "agileBPM";
		jwtService.secret = "asd%WE^@&fas156dfa";
		jwtService.notBeforeMinute = 15L;
		
		System.out.println(JSON.toJSONString(jwtService));
		
		String token = jwtService.generateToken("admin","pc");
		System.out.println(token);
		Claims claims = jwtService.getClaimsFromToken(token);
		System.out.println(claims);
		System.out.println(DateUtil.formatDateTime(claims.getIssuedAt()));
		
	}

}

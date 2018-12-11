package com.dstz.security.jwt.service;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * jwt token获取校验
 * @author jeff
 */
public class JWTService {
    private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 存放 token 的header
	 */
	@Value("${jwt.header:Authorization}")
    private String header;
	public String getJwtHeader() { return header; }
    /**
     * 请求header内的key对应value 的默认开头
     */
	@Value("${jwt.tokenHead:Bearer }")
    private String tokenHead;
	public String getJwtTokenHead() { return tokenHead; }
	
	/***启用jwt模式**/
	@Value("${jwt.enabled:true}")
    private Boolean enabled ;
	public Boolean getJwtEnabled() { return enabled; }
    
	/**
	 *  密钥
	 */
	@Value("${jwt.secret:asd%WE^@&fas156dfa}")
    private String secret;
	
    /**
     * jwt签发者名称
     */
	@Value("${jwt.issuer:agileBPM}")
    private String issuer ;
	
    @Value("${jwt.expiration: 86400}")
    private Long expirationMinute = 24 * 60 * 60L;
    
    @Value("${jwt.notBeforeMinute:15}")
    private Long notBeforeMinute ;
    

	 /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * 从令牌中获取认证的唯一标识
     *
     * @param token 令牌
     * @return 用户id
     */
    public String getSubjectFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
        	logger.debug("从令牌中获取认证失败",e);
            username = null;
        }
        return username;
    }

    /**
     * 验证令牌是否时间有效
     *
     * @param token 令牌
     * @return 是否有效
     */
    public Boolean validateToken(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            Date notBefore = claims.getNotBefore();
            return new Date().after(notBefore) && new Date().before(expiration);
        } catch (Exception e) {
        	logger.debug("验证令牌是否时间有效失败",e);
            return false;
        }
    }

    /**
     * 生成令牌
     *
     * @param username .
     * @return .
     */
    public String generateToken(String username) {
        return Jwts.builder()
                //jwt签发者
                .setIssuer(issuer)
                // jwt所面向的用户
                .setSubject(username)
                //接收jwt的一方
               // .setAudience(audience)
                .setExpiration(new Date(System.currentTimeMillis() + expirationMinute * 60 * 1000))
                .setNotBefore(new Date(System.currentTimeMillis() - notBeforeMinute * 60 * 1000))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
}

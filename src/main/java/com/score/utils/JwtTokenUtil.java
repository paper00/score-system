package com.score.utils;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.score.exception.EXCEPTION_CODE;
import com.score.exception.UserException;

/**
 * @author paper
 * @date 2019/11/06
 * @description JWT Token 工具
 */
@Service
@Slf4j
public class JwtTokenUtil {

    public static final String AUTH_HEADER_KEY = "Authorization";
    public static final String SECRET_KEY = "secret_key";
    
    public static int KEY_SIZE = 512;
    public static String ALGORITHM = "RSA";

    /**
     * 解析 JWT Token
     * @param jsonWebToken
     * @return
     * @throws UserException
     */
    public static Claims parseJWT(String jsonWebToken) throws UserException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (ExpiredJwtException  e) {
            log.error("Token 过期", e);
            throw new UserException(EXCEPTION_CODE.TOKEN_IS_EXPIRATION);
        } catch (Exception e){
            log.error("token 解析异常", e);
            throw new UserException(EXCEPTION_CODE.TOKEN_PARSE_EXCEPTION);
        }
    }
    
    /**
     * 创建 JWT Token
     * @param username
     * @param authorities
     * @return
     * @throws UserException
     */
    public static String createJWT(String username,
    			String authorities, String secretkey) throws UserException {
        try {
            // 使用HS256加密算法
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

            long nowMillis = System.currentTimeMillis();
            Date now = new Date(nowMillis);
            Date exp = new Date(nowMillis + 1000 * 60 * 60 * 72);	// 3天
            
            JwtBuilder builder = Jwts.builder().setHeaderParam("typ", "JWT")
                    .claim("authorities", authorities)
                    .setSubject(username)    // JWT的主体
                    .setIssuedAt(now)        // JWT的签发时间
                    .setExpiration(exp)		 // JWT过期时间
                    .signWith(signatureAlgorithm, SECRET_KEY);
            return builder.compact();
        } catch (Exception e) {
            log.error("签名异常", e);
            throw new UserException(EXCEPTION_CODE.JWT_SIGN_EXCEPTION);
        }
    }
    
    /**
     * 创建公钥密钥对
     * @return
     * @throws UserException
     */
    public static KeyPair getKeyPair() throws UserException {
	      KeyPairGenerator keyPairGenerator = null;
	      try {
	          keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
	      } catch (Exception e) {
	    	  log.error("创建公钥密钥失败", e);
	          throw new UserException(EXCEPTION_CODE.CREATE_KEYPAIR_ERROR);
	      }
	      keyPairGenerator.initialize(KEY_SIZE);
	      return keyPairGenerator.generateKeyPair();
    }

    /**
     * 获取用户名
     * @param token
     * @return
     * @throws UserException
     */
    public static String getUsernameByToken(String token, String secretkey)
    		throws UserException {
        return parseJWT(token).getSubject();
    }

    /**
     * 判断是否过期
     * @param token
     * @return
     * @throws UserException
     */
    public static boolean isExpiration(String token, String secretkey) 
    		throws UserException {
    	long now = System.currentTimeMillis();
        return parseJWT(token).getExpiration().before(new Date(now));
    }
}

package com.peintre.utils;

import com.peintre.config.BizException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Program: myblog
 * @Description: JWT工具类
 * @Author: Peintre
 * @Date: 2023/3/14 22:32
 **/
@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -5625635588908941275L;
    //创建对象主体
    private static final String CLAIM_KEY_USERNAME = "SUBJECT";
    //创建创建时间
    private static final String CLAIM_KEY_CREATED = "CREATE_TIME";


    //@Value这个注解一定要引入spring-boot-starter-validation才能使用
    //@Value注解可以代替@Data和@ConfigurationProperties结合
    //这两个二者选一即可
    //我建议使用@Data和@ConfigurationProperties结合
    @Value("${jwt.secretKey}")
    private String SECRET;//创建加密盐

    //过期时间
    @Value("${jwt.expireTime}")
    private Long EXPIRE_TIME;

    //使用@PostConstruct注解修饰的init方法就会在Spring容器的启动时自动的执行
    @PostConstruct
    private void init() {
        log.info("JwtTokenUtil初始化");
    }


    //根据用户名生成token
    public String createToken(String username) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

    //根据负载生成jwt token
    private String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(expirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    //根据token获取用户名
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.get(CLAIM_KEY_USERNAME).toString();
        }catch (Exception e){
            throw new BizException("解析token失败");
        }
        return username;
    }

    //判断是否过期，过期返回true
    public boolean isTokenExpired(String token) {
        Claims claims = getClaimsFromToken(token);
        Date expiredDate = claims.getExpiration();
        return expiredDate.before(new Date());
    }

    //从token中获取荷载
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }

    //token失效时间：系统当前毫秒数+我们设置的时间（s）*1000=》毫秒
    private Date expirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRE_TIME * 1000);
    }

    //判断token是否有效
    public boolean validateToken(String token, String username) {
        String usernameFromToken = getUsernameFromToken(token);
        return usernameFromToken.equals(username);
    }

    //判断token是否可以被刷新
    //过期（销毁）就可以
    public boolean canBeRefreshed(String token) {
        return !isTokenExpired(token);
    }

    //刷新token
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        //修改为当前时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return createToken(claims);
    }

}

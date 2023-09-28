package com.peintre.filter;

import cn.hutool.core.util.StrUtil;
import com.peintre.config.BizException;
import com.peintre.enums.StatusCode;
import com.peintre.service.serurityImpl.UserDetailsServiceImpl;
import com.peintre.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Program: myblog
 * @Description: 有关token的请求处理
 * @Author: Peintre
 * @Date: 2023/3/14 22:59
 **/
@Component
public class TokenHandlerFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求路径
        String path = request.getServletPath();
        //从请求头中获取token
        String token = request.getHeader(tokenHeader);
        if(token==null || StrUtil.isBlank(tokenHeader)){
            if("/login".equals(path)){
                filterChain.doFilter(request, response);
                return;
            }
            throw new BizException(StatusCode.NO_LOGIN);
        }
        //token过期处理
        if(jwtUtil.isTokenExpired(token)){
            throw new BizException(StatusCode.USER_LOGIN_EXPIRED);
        }
        //根据token获取用户名
        String username  = jwtUtil.getUsernameFromToken(token);
        //获取用户账号及权限相关信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //创建一个新的SecurityContext 实例
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        filterChain.doFilter(request, response);
    }
}

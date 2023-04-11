package com.peintre.filter;

import com.peintre.service.serurityImpl.UserDetailsServiceImpl;
import com.peintre.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
        //从请求头中获取token
        String token = request.getHeader(tokenHeader);
        if(token==null || token.equals("")){
            //TODO 跳转登录界面
            return;
        }
        //token过期处理
        if(jwtUtil.isTokenExpired(token)){
            //TODO 跳转登录界面
            return;
        }
        //根据token获取用户名
        String username  = jwtUtil.getUsernameFromToken(token);
        //获取用户信息并设置到security得上下文中后，就不会再去走认证流程
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
}

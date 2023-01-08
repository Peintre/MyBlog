package com.peintre.config;

import com.peintre.Interceptor.PageableHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Spring内部的一种配置方式，采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制，
 * 可以自定义一些Handler，Interceptor，ViewResolver，MessageConverter。
 * 基于java-based方式的spring mvc配置，需要创建一个配置类并实现WebMvcConfigurer 接口
 */



/**
 * @Program: myblog
 * @Description: WebMVC配置
 * @Author: Peintre
 * @Date: 2022/12/3 14:30
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new PageableHandlerInterceptor());
    }
}

package com.peintre.handler;

import com.alibaba.fastjson.JSON;
import com.peintre.utils.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.peintre.constant.CommonConst.APPLICATION_JSON;

/**
 * @Program: myblog
 * @Description: 登录失败处理类
 * @Author: Peintre
 * @Date: 2022/11/10 9:38
 **/
@Component
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.fail(exception.getMessage())));
    }
}

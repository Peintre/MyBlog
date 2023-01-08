package com.peintre.handler;

import com.alibaba.fastjson.JSON;
import com.peintre.utils.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.peintre.constant.CommonConst.APPLICATION_JSON;

/**
 * @Program: myblog
 * @Description: 注销成功处理类
 * @Author: Peintre
 * @Date: 2022/11/10 9:42
 **/
@Component
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok("注销成功")));
    }
}

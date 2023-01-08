package com.peintre.handler;

import com.alibaba.fastjson.JSON;
import com.peintre.utils.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.peintre.constant.CommonConst.APPLICATION_JSON;

/**
 * 用户权限处理
 *
 * @author Peintre
 * @date 2021/07/28
 */
@Component
public class MyAccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException {
        httpServletResponse.setContentType(APPLICATION_JSON);
        httpServletResponse.getWriter().write(JSON.toJSONString(Result.fail("权限不足111")));
    }

}

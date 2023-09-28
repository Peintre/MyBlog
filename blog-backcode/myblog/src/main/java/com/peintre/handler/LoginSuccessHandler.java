package com.peintre.handler;

import com.alibaba.fastjson.JSON;
import com.peintre.dao.UserAuthDao;
import com.peintre.dto.UserInfoDTO;
import com.peintre.entity.UserAuth;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.JwtTokenUtil;
import com.peintre.utils.Result;
import com.peintre.utils.UserUtils;
import com.peintre.vo.LoginSuccessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static com.peintre.constant.CommonConst.APPLICATION_JSON;

/**
 * @Program: myblog
 * @Description: 登录成功处理类
 * @Author: Peintre
 * @Date: 2022/11/10 9:26
 **/
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserAuthDao userAuthDao;

    @Autowired
    private JwtTokenUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //返回给前台用户的相关信息
        UserInfoDTO userLoginDTO = BeanCopyUtils.copyObject(UserUtils.getLoginUser(), UserInfoDTO.class);
        String token = jwtUtil.createToken(userLoginDTO.getUsername());
        response.setContentType(APPLICATION_JSON);
        response.getWriter().write(JSON.toJSONString(Result.ok(new LoginSuccessVo(userLoginDTO,token))));
        // 更新用户ip，最近登录时间
        updateUserInfo();
    }

    /**
     * 更新用户ip相关信息
     */
    @Async
    public void updateUserInfo() {
        UserAuth userAuth = UserAuth.builder()
                .id(UserUtils.getLoginUser().getId())
                .ipAddress(UserUtils.getLoginUser().getIpAddress())
                .ipSource(UserUtils.getLoginUser().getIpSource())
                .lastLoginTime(UserUtils.getLoginUser().getLastLoginTime())
                .build();
        userAuthDao.updateById(userAuth);
    }
}

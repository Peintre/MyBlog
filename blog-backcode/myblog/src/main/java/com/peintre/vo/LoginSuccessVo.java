package com.peintre.vo;

import com.peintre.dto.UserInfoDTO;
import lombok.Data;

/**
 * @author: peintre
 * @date: 2023/9/28
 * @description: 登录成功vo
 */
@Data
public class LoginSuccessVo {
    /**
     * 用户信息
     */
    private UserInfoDTO userInfo;

    /**
     * token
     */
    private String token;

    public LoginSuccessVo() {
    }

    public LoginSuccessVo(UserInfoDTO userInfo, String token) {
        this.userInfo = userInfo;
        this.token = token;
    }
}

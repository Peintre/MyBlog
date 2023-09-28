package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.UserBackDTO;
import com.peintre.entity.UserAuth;
import com.peintre.vo.*;

/**
 * @Program: MyBlog
 * @Description: (tb_user_auth)表服务接口
 * @Author: Peintre
 * @Date: 2022-11-09 16:22:35
 **/
public interface UserAuthService extends IService<UserAuth> {

    /**
     * 修改管理员密码
     *
     * @param passwordVO 密码对象
     */
    void updateAdminPassword(PasswordVO passwordVO);

    /**
     * 查询后台用户列表
     *
     * @param condition 条件
     * @return 用户列表
     */
    PageResultVo<UserBackDTO> getUserList(ConditionVo condition);

    /**
     * 发送邮箱验证码
     *
     * @param username 邮箱号
     */
    void sendCode(String username,Integer state);

    /**
     * 用户注册
     *
     * @param user 用户对象
     */
    void register(UserVo user);

    /**
     * 修改密码
     *
     * @param user 用户对象
     */
    void updatePassword(UserVo user);
}


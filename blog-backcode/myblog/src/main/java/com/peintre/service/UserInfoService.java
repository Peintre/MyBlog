package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.UserOnlineDTO;
import com.peintre.entity.UserInfo;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.UserInfoVO;
import com.peintre.vo.UserRoleVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Program: MyBlog
 * @Description: (tb_user_info)表服务接口
 * @Author: Peintre
 * @Date: 2022-11-10 12:21:18
 **/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 修改用户头像
     *
     * @param file 头像图片
     * @return 头像地址
     */
    String updateUserAvatar(MultipartFile file);

    /**
     * 修改用户基本信息
     *
     * @param userInfoVO 用户信息类
     * @return void
     */
    void updateUserInfo(UserInfoVO userInfoVO);

    /**
     * 更新用户角色
     *
     * @param userRoleVo 更新用户角色
     */
    void updateUserRole(UserRoleVo userRoleVo);

    /**
     * 查看在线用户列表
     *
     * @param conditionVo 条件
     * @return 在线用户列表
     */
    PageResultVo<UserOnlineDTO> listOnlineUsers(ConditionVo conditionVo);

}


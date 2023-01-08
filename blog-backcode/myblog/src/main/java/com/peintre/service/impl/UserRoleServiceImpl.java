package com.peintre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.UserRoleDao;
import com.peintre.entity.UserRole;
import com.peintre.service.UserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Program: MyBlog
 * @Description: (tb_user_role)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-04 13:54:42
 **/
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
   
    @Autowired
    private UserRoleDao userRoleDao;
}


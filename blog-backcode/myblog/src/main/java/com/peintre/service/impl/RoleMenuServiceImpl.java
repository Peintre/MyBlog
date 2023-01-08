package com.peintre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.RoleMenuDao;
import com.peintre.entity.RoleMenu;
import com.peintre.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Program: MyBlog
 * @Description: (tb_role_menu)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-04 12:39:04
 **/
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenu> implements RoleMenuService {
   
    @Autowired
    private RoleMenuDao roleMenuDao;
}


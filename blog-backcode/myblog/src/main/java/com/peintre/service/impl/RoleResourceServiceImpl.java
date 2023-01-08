package com.peintre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.RoleResourceDao;
import com.peintre.entity.RoleResource;
import com.peintre.service.RoleResourceService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Program: MyBlog
 * @Description: (tb_role_resource)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-04 12:44:30
 **/
@Service("roleResourceService")
public class RoleResourceServiceImpl extends ServiceImpl<RoleResourceDao, RoleResource> implements RoleResourceService {
   
    @Autowired
    private RoleResourceDao roleResourceDao;
}


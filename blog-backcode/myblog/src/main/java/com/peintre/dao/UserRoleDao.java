package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.UserRole;


/**
 * @Program: MyBlog
 * @Description: (tb_user_role)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-04 13:54:42
 **/
@Repository
public interface UserRoleDao extends BaseMapper<UserRole> {

}


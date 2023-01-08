package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.RoleMenu;


/**
 * @Program: MyBlog
 * @Description: (tb_role_menu)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-04 12:37:55
 **/
@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenu> {

}


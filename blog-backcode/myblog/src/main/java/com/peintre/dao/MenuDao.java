package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Menu;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_menu)表数据访问层
 * @Author: Peintre
 * @Date: 2022-11-10 13:16:16
 **/
@Repository
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> listMenusByUserInfoId(Integer userInfoId);
}


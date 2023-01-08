package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Page;


/**
 * @Program: MyBlog
 * @Description: (tb_page)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-22 10:44:25
 **/
@Repository
public interface PageDao extends BaseMapper<Page> {

}


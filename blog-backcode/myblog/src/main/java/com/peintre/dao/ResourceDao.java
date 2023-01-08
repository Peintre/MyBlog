package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Resource;


/**
 * @Program: MyBlog
 * @Description: (tb_resource)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-03 16:04:23
 **/
@Repository
public interface ResourceDao extends BaseMapper<Resource> {

}


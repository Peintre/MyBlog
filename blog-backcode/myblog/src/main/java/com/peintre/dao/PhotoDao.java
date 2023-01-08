package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Photo;


/**
 * @Program: MyBlog
 * @Description: (tb_photo)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-27 18:11:51
 **/
@Repository
public interface PhotoDao extends BaseMapper<Photo> {

}


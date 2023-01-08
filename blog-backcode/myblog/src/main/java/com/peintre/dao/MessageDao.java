package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Message;


/**
 * @Program: MyBlog
 * @Description: (tb_message)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-21 17:07:22
 **/
@Repository
public interface MessageDao extends BaseMapper<Message> {

}


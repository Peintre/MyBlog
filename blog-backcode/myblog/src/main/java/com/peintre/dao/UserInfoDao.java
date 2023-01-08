package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.UserInfo;


/**
 * @Program: MyBlog
 * @Description: (tb_user_info)表数据访问层
 * @Author: Peintre
 * @Date: 2022-11-10 12:21:18
 **/
@Repository
public interface UserInfoDao extends BaseMapper<UserInfo> {

}


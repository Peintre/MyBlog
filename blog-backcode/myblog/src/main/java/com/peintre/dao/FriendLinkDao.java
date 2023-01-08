package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.FriendLink;


/**
 * @Program: MyBlog
 * @Description: (tb_friend_link)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-22 11:04:28
 **/
@Repository
public interface FriendLinkDao extends BaseMapper<FriendLink> {

}


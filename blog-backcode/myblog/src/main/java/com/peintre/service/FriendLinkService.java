package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.FriendLinkBackDTO;
import com.peintre.entity.FriendLink;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.FriendLinkVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;

/**
 * @Program: MyBlog
 * @Description: (tb_friend_link)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-22 11:04:28
 **/
public interface FriendLinkService extends IService<FriendLink> {

    /**
     * (后台)查询友链信息
     *
     * @param condition 条件
     * @return 友链列表
     */
    PageResultVo<FriendLinkBackDTO> listFriendLinkDTO(ConditionVo condition);

    /**
     * 添加/修改友链
     *
     * @param friendLinkVo 友链
     */
    void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo);

    /**
     * 删除友链
     *
     * @param idsVo 友链id集合
     */
    void deleteFriendLink(IdsVo idsVo);
}


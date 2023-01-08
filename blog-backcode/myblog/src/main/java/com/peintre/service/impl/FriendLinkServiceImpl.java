package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.FriendLinkDao;
import com.peintre.dto.FriendLinkBackDTO;
import com.peintre.entity.FriendLink;
import com.peintre.service.FriendLinkService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.FriendLinkVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_friend_link)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-22 11:04:28
 **/
@Service("friendLinkService")
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkDao, FriendLink> implements FriendLinkService {
   
    @Autowired
    private FriendLinkDao friendLinkDao;

    @Override
    public PageResultVo<FriendLinkBackDTO> listFriendLinkDTO(ConditionVo condition) {
        // 分页查询友链列表
        Page<FriendLink> page = new Page<>(PageUtils.getCurrent(), PageUtils.getSize());
        Page<FriendLink> friendLinkPage = friendLinkDao.selectPage(page, new LambdaQueryWrapper<FriendLink>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), FriendLink::getLinkName, condition.getKeywords()));
        // 转换DTO
        List<FriendLinkBackDTO> friendLinkBackDTOList = BeanCopyUtils.copyList(friendLinkPage.getRecords(), FriendLinkBackDTO.class);
        return new PageResultVo<>(friendLinkBackDTOList, (int) friendLinkPage.getTotal());
    }

    @Override
    public void saveOrUpdateFriendLink(FriendLinkVo friendLinkVo) {
        FriendLink friendLink = BeanCopyUtils.copyObject(friendLinkVo, FriendLink.class);
        this.saveOrUpdate(friendLink);
    }

    @Override
    public void deleteFriendLink(IdsVo idsVo) {
        friendLinkDao.deleteBatchIds(idsVo.getIdList());
    }
}


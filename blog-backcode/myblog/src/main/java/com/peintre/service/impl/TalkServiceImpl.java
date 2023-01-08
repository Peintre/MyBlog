package com.peintre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.TalkDao;
import com.peintre.dto.TalkBackDTO;
import com.peintre.dto.blog.TalkDTO;
import com.peintre.entity.Talk;
import com.peintre.service.TalkService;
import com.peintre.utils.*;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.TalkVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.peintre.enums.ArticleStatusEnum.PUBLIC;

/**
 * @Program: MyBlog
 * @Description: (tb_talk)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-22 11:36:49
 **/
@Service("talkService")
public class TalkServiceImpl extends ServiceImpl<TalkDao, Talk> implements TalkService {
   
    @Autowired
    private TalkDao talkDao;

    @Override
    public PageResultVo<TalkBackDTO> listBackTalks(ConditionVo conditionVo) {
        // 查询说说总量
        Integer count = talkDao.selectCount(new LambdaQueryWrapper<Talk>()
                .eq(Objects.nonNull(conditionVo.getStatus()), Talk::getStatus, conditionVo.getStatus()));
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 分页查询说说
        List<TalkBackDTO> talkDTOList = talkDao.listBackTalks(PageUtils.getLimitCurrent(), PageUtils.getSize(), conditionVo);
        talkDTOList.forEach(item -> {
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResultVo<>(talkDTOList, count);
    }

    @Override
    public TalkBackDTO getBackTalkById(Integer talkId) {
        TalkBackDTO talkBackDTO = talkDao.getBackTalkById(talkId);
        // 转换图片格式
        if (Objects.nonNull(talkBackDTO.getImages())) {
            talkBackDTO.setImgList(CommonUtils.castList(JSON.parseObject(talkBackDTO.getImages(), List.class), String.class));
        }
        return talkBackDTO;
    }

    @Override
    public void saveOrUpdateTalk(TalkVo talkVo) {
        Talk talk = BeanCopyUtils.copyObject(talkVo, Talk.class);
        talk.setUserId(UserUtils.getLoginUser().getUserInfoId());
        this.saveOrUpdate(talk);
    }

    @Override
    public void deleteTalks(IdsVo idsVo) {
        talkDao.deleteBatchIds(idsVo.getIdList());
    }

    @Override
    public List<String> listHomeTalks() {
        // 查询最新的5条说说
        return talkDao.selectList(new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus())
                .orderByDesc(Talk::getIsTop)
                .orderByDesc(Talk::getId)
                .last("limit 5"))
                .stream()
                .map(item -> item.getContent().length() > 200 ? HtmlUtils.deleteHMTLTag(item.getContent().substring(0, 200)) : HtmlUtils.deleteHMTLTag(item.getContent()))
                .collect(Collectors.toList());
    }

    @Override
    public PageResultVo<TalkDTO> listTalks() {
        // 查询说说总量
        Integer count = talkDao.selectCount((new LambdaQueryWrapper<Talk>()
                .eq(Talk::getStatus, PUBLIC.getStatus())));
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 分页查询说说
        List<TalkDTO> talkDTOList = talkDao.listTalks(PageUtils.getLimitCurrent(), PageUtils.getSize());
//        // 查询说说评论量
//        List<Integer> talkIdList = talkDTOList.stream()
//                .map(TalkDTO::getId)
//                .collect(Collectors.toList());
//        Map<Integer, Integer> commentCountMap = commentDao.listCommentCountByTopicIds(talkIdList)
//                .stream()
//                .collect(Collectors.toMap(CommentCountDTO::getId, CommentCountDTO::getCommentCount));
//        // 查询说说点赞量
//        Map<String, Object> likeCountMap = redisService.hGetAll(TALK_LIKE_COUNT);
        talkDTOList.forEach(item -> {
//            item.setLikeCount((Integer) likeCountMap.get(item.getId().toString()));
//            item.setCommentCount(commentCountMap.get(item.getId()));
            // 转换图片格式
            if (Objects.nonNull(item.getImages())) {
                item.setImgList(CommonUtils.castList(JSON.parseObject(item.getImages(), List.class), String.class));
            }
        });
        return new PageResultVo<>(talkDTOList, count);
    }
}


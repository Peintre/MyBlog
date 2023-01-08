package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.TalkBackDTO;
import com.peintre.dto.blog.TalkDTO;
import com.peintre.entity.Talk;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.TalkVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_talk)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-22 11:36:49
 **/
public interface TalkService extends IService<Talk> {

    /**
     * (后台)查看说说信息
     *
     * @param conditionVo 条件
     * @return {@link PageResultVo<TalkBackDTO>}
     */
    PageResultVo<TalkBackDTO> listBackTalks(ConditionVo conditionVo);

    /**
     * (后台)根据id查看说说信息
     *
     * @param talkId 说说id
     * @return {@link TalkBackDTO} 说说信息
     */
    TalkBackDTO getBackTalkById(Integer talkId);

    /**
     * 添加/修改说说
     *
     * @param talkVo 说说信息
     */
    void saveOrUpdateTalk(TalkVo talkVo);

    /**
     * 删除说说
     *
     * @param idsVo 说说id集合
     */
    void deleteTalks(IdsVo idsVo);

    /**
     * (前台)查询首页说说
     *
     * @return {@link List <String>} 说说列表
     */
    List<String> listHomeTalks();

    /**
     * (前台)查询说说
     *
     * @return {@link PageResultVo<TalkDTO>} 说说列表
     */
    PageResultVo<TalkDTO> listTalks();
}


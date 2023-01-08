package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.TalkBackDTO;
import com.peintre.dto.blog.TalkDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Talk;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_talk)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-22 11:36:49
 **/
@Repository
public interface TalkDao extends BaseMapper<Talk> {

    /**
     * (后台)查看说说信息
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List <TalkBackDTO>}
     */
    List<TalkBackDTO> listBackTalks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);


    /**
     * (后台)根据id查看说说信息
     *
     * @param talkId 说说id
     * @return {@link TalkBackDTO} 说说信息
     */
    TalkBackDTO getBackTalkById(@Param("talkId") Integer talkId);

    /**
     * 获取说说列表
     *
     * @param current 页码
     * @param size    大小
     * @return {@link List<TalkDTO>}
     */
    List<TalkDTO> listTalks(@Param("current") Long current, @Param("size") Long size);
}


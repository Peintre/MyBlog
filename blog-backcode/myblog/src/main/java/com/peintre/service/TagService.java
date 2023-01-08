package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.TagBackDTO;
import com.peintre.dto.TagDTO;
import com.peintre.entity.Tag;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.TagVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_tag)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-18 18:33:05
 **/
public interface TagService extends IService<Tag> {

    /**
     * 查询文章标签
     *
     * @param condition 条件
     * @return {@link List <TagDTO>} 标签列表
     */
    List<TagDTO> listTagsBySearch(ConditionVo condition);

    /**
     * 查询后台标签
     *
     * @param condition 条件
     * @return {@link PageResultVo<TagBackDTO>} 标签列表
     */
    PageResultVo<TagBackDTO> listTagBackDTO(ConditionVo condition);

    /**
     * 保存或更新标签
     *
     * @param tagVo 标签
     */
    void addOrUpdateTag(TagVo tagVo);

    /**
     * 删除标签
     *
     * @param idsVo 标签id集合
     */
    void deleteTag(IdsVo idsVo);

    /**
     * (前台)查询标签
     *
     * @return 标签列表
     */
    PageResultVo<TagDTO> listTags();
}


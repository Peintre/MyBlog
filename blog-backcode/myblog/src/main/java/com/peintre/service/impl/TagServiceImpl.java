package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.ArticleTagDao;
import com.peintre.dao.TagDao;
import com.peintre.dto.TagBackDTO;
import com.peintre.dto.TagDTO;
import com.peintre.entity.ArticleTag;
import com.peintre.entity.Tag;
import com.peintre.service.TagService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import com.peintre.vo.TagVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Program: MyBlog
 * @Description: (tb_tag)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-18 18:33:05
 **/
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagDao, Tag> implements TagService {
   
    @Autowired
    private TagDao tagDao;

    @Autowired
    private ArticleTagDao articleTagDao;

    @Override
    public List<TagDTO> listTagsBySearch(ConditionVo condition) {
        // 搜索标签
        List<Tag> tagList = tagDao.selectList(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords())
                .orderByDesc(Tag::getId));
        return BeanCopyUtils.copyList(tagList, TagDTO.class);
    }

    @Override
    public PageResultVo<TagBackDTO> listTagBackDTO(ConditionVo condition) {
        // 查询标签数量
        Integer count = tagDao.selectCount(new LambdaQueryWrapper<Tag>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Tag::getTagName, condition.getKeywords()));
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 分页查询标签列表
        List<TagBackDTO> tagList = tagDao.listTagBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResultVo<>(tagList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateTag(TagVo tagVo) {
        // 查询标签名是否存在
        Tag existTag = tagDao.selectOne(new LambdaQueryWrapper<Tag>()
                .select(Tag::getId)
                .eq(Tag::getTagName, tagVo.getTagName()));
        if (Objects.nonNull(existTag) && !existTag.getId().equals(tagVo.getId())) {
            throw new BizException("标签名已存在");
        }
        Tag tag = BeanCopyUtils.copyObject(tagVo, Tag.class);
        this.saveOrUpdate(tag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteTag(IdsVo idsVo) {
        // 查询标签下是否有文章
        Integer count = articleTagDao.selectCount(new LambdaQueryWrapper<ArticleTag>()
                .in(ArticleTag::getTagId, idsVo.getIdList()));
        if (count > 0) {
            throw new BizException("删除失败，该标签下存在文章");
        }
        tagDao.deleteBatchIds(idsVo.getIdList());
    }

    @Override
    public PageResultVo<TagDTO> listTags() {
        // 查询标签列表
        List<Tag> tagList = tagDao.selectList(null);
        // 转换DTO
        List<TagDTO> tagDTOList = BeanCopyUtils.copyList(tagList, TagDTO.class);
        // 查询标签数量
        Integer count = tagDao.selectCount(null);
        return new PageResultVo<>(tagDTOList, count);
    }
}


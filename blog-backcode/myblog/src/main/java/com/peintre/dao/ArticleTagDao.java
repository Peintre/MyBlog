package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.ArticleTag;


/**
 * @Program: MyBlog
 * @Description: (tb_article_tag)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-21 14:28:17
 **/
@Repository
public interface ArticleTagDao extends BaseMapper<ArticleTag> {

}


package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.ArticleBackDTO;
import com.peintre.dto.blog.ArticleBlogDTO;
import com.peintre.dto.blog.ArticleDTO;
import com.peintre.dto.blog.ArticlePreviewDTO;
import com.peintre.dto.blog.ArticleRecommendDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Article;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_article)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-18 16:42:19
 **/
@Repository
public interface ArticleDao extends BaseMapper<Article> {

    /**
     * 查询后台文章总量
     *
     * @param condition 条件
     * @return 文章总量
     */
    Integer countArticleBacks(@Param("condition") ConditionVo condition);

    /**
     * 根据条件查询后台文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticleBackDTO> listArticleBacks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

    /**
     * (前台)查询文章
     *
     * @param current 页码
     * @param size    大小
     * @return 文章列表
     */
    List<ArticleBlogDTO> listArticles(@Param("current") Long current, @Param("size") Long size);

    /**
     * 查看文章的推荐文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    List<ArticleRecommendDTO> listRecommendArticles(@Param("articleId") Integer articleId);

    /**
     * 根据id查询文章
     *
     * @param articleId 文章id
     * @return 文章信息
     */
    ArticleDTO getArticleById(@Param("articleId") Integer articleId);

    /**
     * 根据条件查询文章
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return 文章列表
     */
    List<ArticlePreviewDTO> listArticlesByCondition(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

}


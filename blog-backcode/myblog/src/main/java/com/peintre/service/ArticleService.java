package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.ArticleBackDTO;
import com.peintre.dto.blog.ArchiveArticleDTO;
import com.peintre.dto.blog.ArticleBlogDTO;
import com.peintre.dto.blog.ArticleDTO;
import com.peintre.dto.blog.ArticlePreviewListDTO;
import com.peintre.entity.Article;
import com.peintre.vo.*;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_article)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-18 16:34:08
 **/
public interface ArticleService extends IService<Article> {


    /**
     * 查询后台文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    PageResultVo<ArticleBackDTO> listArticleBacks(ConditionVo condition);


    /**
     * 导出文章
     *
     * @param articleIdList 文章id列表
     * @return {@link List}<{@link String}> 文件地址
     */
    List<String> exportArticles(List<Integer> articleIdList);

    /**
     * 添加/修改文章
     *
     * @param articleVo 文章信息
     */
    void saveOrUpdateArticle(ArticleVo articleVo);

    /**
     * 删除/恢复文章(逻辑删除/恢复)
     *
     * @param idsVo id列表
     * @param status 文章状态
     */
    void updateArticlesStatus(IdsVo idsVo, Integer status);

    /**
     * 删除文章(物理删除)
     *
     * @param idsVo id列表
     */
    void deleteArticles(IdsVo idsVo);

    /**
     * (后台)根据id查看文章
     *
     * @param articleId 文章id
     * @return 文章列表
     */
    ArticleVo getArticleBackById(Integer articleId);

    /**
     * 修改文章置顶
     *
     * @param articleTopVo 文章置顶信息
     */
    void updateArticleTop(ArticleTopVo articleTopVo);

    /**
     * (前台)查询文章
     *
     * @return 文章列表
     */
    List<ArticleBlogDTO> listArticles();

    /**
     * (前台)查询文章归档
     *
     * @return 文章归档
     */
    PageResultVo<ArchiveArticleDTO> listArchives();

    /**
     * (前台)根据id查询文章
     *
     * @param articleId 文章id
     * @return {@link ArticleDTO} 文章信息
     */
    ArticleDTO getArticleById(Integer articleId);

    /**
     * (前台)条件查询文章
     *
     * @param condition 条件
     * @return 文章列表
     */
    ArticlePreviewListDTO listArticlesByCondition(ConditionVo condition);
}


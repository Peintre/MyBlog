package com.peintre.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Program: MyBlog
 * @Description: (tb_article)表实体
 * @Author: Peintre
 * @Date: 2022-12-18 16:34:08
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_article")
public class Article implements Serializable{

    private static final long serialVersionUID = 1L;
       

    /**
     * 文章ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    @TableField("article_title")
    private String articleTitle;

    /**
     * 内容
     */
    @TableField("article_content")
    private String articleContent;

    /**
     * 文章分类
     */
    @TableField("category_id")
    private Integer categoryId;

    /**
     * 文章缩略图
     */
    @TableField("article_cover")
    private String articleCover;

    /**
     * 作者
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 是否置顶 0否 1是
     */
    @TableField("is_top")
    private Integer isTop;

    /**
     * 是否删除  0否 1是
     */
    @TableField("is_delete")
    private Integer isDelete;

    /**
     * 原文链接
     */
    @TableField("original_url")
    private String originalUrl;

    /**
     * 状态值 1公开 2私密 3评论可见
     */
    @TableField("status")
    private Integer status;

    /**
     * 文章类型 1原创 2转载 3翻译
     */
    @TableField("type")
    private Integer type;

    /**
     * 发表时间
     */
    @TableField(value="create_time",fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value="update_time",fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

}



package com.peintre.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Program: myblog
 * @Description: 文章上下篇
 * @Author: Peintre
 * @Date: 2023/1/1 22:05
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePaginationDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 文章缩略图
     */
    private String articleCover;

    /**
     * 标题
     */
    private String articleTitle;
}

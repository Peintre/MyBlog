package com.peintre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Program: myblog
 * @Description: 分类标签信息
 * @Author: Peintre
 * @Date: 2022/12/21 15:40
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryBackDTO {
    /**
     * 分类id
     */
    private Integer id;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}

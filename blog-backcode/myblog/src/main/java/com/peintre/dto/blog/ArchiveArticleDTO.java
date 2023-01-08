package com.peintre.dto.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Program: myblog
 * @Description: 文章信息
 * @Author: Peintre
 * @Date: 2022/12/31 14:02
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArchiveArticleDTO {
    /**
     * id
     */
    private Integer id;

    /**
     * 标题
     */
    private String articleTitle;

    /**
     * 发表时间
     */
    private LocalDateTime createTime;
}

package com.peintre.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Program: myblog
 * @Description: 文章状态枚举
 * @Author: Peintre
 * @Date: 2022/12/21 14:10
 **/
@Getter
@AllArgsConstructor
public enum ArticleStatusEnum {
    /**
     * 公开
     */
    PUBLIC(1, "公开"),
    /**
     * 私密
     */
    SECRET(2, "私密"),
    /**
     * 草稿
     */
    DRAFT(3, "草稿");

    /**
     * 状态
     */
    private final Integer status;

    /**
     * 描述
     */
    private final String desc;
}

package com.peintre.dto.blog;

import com.peintre.vo.PageVo;
import com.peintre.vo.WebsiteConfigVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Program: myblog
 * @Description: 网站配置信息
 * @Author: Peintre
 * @Date: 2022/12/29 22:29
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebConfigDTO {

    /**
     * 网站配置
     */
    private WebsiteConfigVo websiteConfig;
    /**
     * 页面列表
     */
    private List<PageVo> pageList;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;

}

package com.peintre.service;

import com.peintre.dto.blog.WebConfigDTO;
import com.peintre.vo.BlogInfoVo;
import com.peintre.vo.WebsiteConfigVo;

/**
 * @Program: myblog
 * @Description: 博客信息服务
 * @Author: Peintre
 * @Date: 2022/12/21 12:32
 **/
public interface BlogInfoService {

    /**
     * 获取网站配置
     *
     * @return {@link WebsiteConfigVo} 网站配置
     */
    WebsiteConfigVo getWebsiteConfig();

    /**
     * 获取关于我内容
     *
     * @return 关于我内容
     */
    String getAbout();

    /**
     * 修改关于我内容
     *
     * @param blogInfoVo 博客信息
     */
    void updateAbout(BlogInfoVo blogInfoVo);

    /**
     * (前台)获取网站配置
     *
     */
    WebConfigDTO getWebsiteConfigBlog();
}

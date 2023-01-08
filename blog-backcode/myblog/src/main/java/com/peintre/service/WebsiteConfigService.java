package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.entity.WebsiteConfig;
import com.peintre.vo.WebsiteConfigVo;

/**
 * @Program: MyBlog
 * @Description: (tb_website_config)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-22 10:26:55
 **/
public interface WebsiteConfigService extends IService<WebsiteConfig> {

    /**
     * 更新网站配置
     *
     * @param websiteConfigVo 网站配置
     */
    void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo);
}


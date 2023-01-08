package com.peintre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.WebsiteConfigDao;
import com.peintre.entity.WebsiteConfig;
import com.peintre.service.RedisService;
import com.peintre.service.WebsiteConfigService;
import com.peintre.vo.WebsiteConfigVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import static com.peintre.constant.RedisPrefixConst.WEBSITE_CONFIG;

/**
 * @Program: MyBlog
 * @Description: (tb_website_config)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-22 10:27:01
 **/
@Service("websiteConfigService")
public class WebsiteConfigServiceImpl extends ServiceImpl<WebsiteConfigDao, WebsiteConfig> implements WebsiteConfigService {
   
    @Autowired
    private WebsiteConfigDao websiteConfigDao;

    @Autowired
    private RedisService redisService;

    @Override
    public void updateWebsiteConfig(WebsiteConfigVo websiteConfigVo) {
        // 修改网站配置
        WebsiteConfig websiteConfig = WebsiteConfig.builder()
                .id(1)
                .config(JSON.toJSONString(websiteConfigVo))
                .build();
        websiteConfigDao.updateById(websiteConfig);
        // 删除缓存
        redisService.del(WEBSITE_CONFIG);
    }
}


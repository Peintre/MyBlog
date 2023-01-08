package com.peintre.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.peintre.dao.ArticleDao;
import com.peintre.dao.CategoryDao;
import com.peintre.dao.TagDao;
import com.peintre.dao.WebsiteConfigDao;
import com.peintre.dto.blog.WebConfigDTO;
import com.peintre.entity.Article;
import com.peintre.service.BlogInfoService;
import com.peintre.service.PageService;
import com.peintre.service.RedisService;
import com.peintre.vo.BlogInfoVo;
import com.peintre.vo.PageVo;
import com.peintre.vo.WebsiteConfigVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.peintre.constant.CommonConst.DEFAULT_CONFIG_ID;
import static com.peintre.constant.CommonConst.FALSE;
import static com.peintre.constant.RedisPrefixConst.ABOUT;
import static com.peintre.constant.RedisPrefixConst.WEBSITE_CONFIG;
import static com.peintre.enums.ArticleStatusEnum.PUBLIC;

/**
 * @Program: myblog
 * @Description: 博客信息服务实现类
 * @Author: Peintre
 * @Date: 2022/12/21 12:36
 **/
@Service
public class BlogInfoServiceImpl implements BlogInfoService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private WebsiteConfigDao websiteConfigDao;

    @Autowired
    private PageService pageService;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public WebsiteConfigVo getWebsiteConfig() {
        WebsiteConfigVo websiteConfigVo;
        // 获取缓存数据
        Object websiteConfig = redisService.get(WEBSITE_CONFIG);
        if (Objects.nonNull(websiteConfig)) {
            websiteConfigVo = JSON.parseObject(websiteConfig.toString(), WebsiteConfigVo.class);
        } else {
            // 从数据库中加载
            String config = websiteConfigDao.selectById(DEFAULT_CONFIG_ID).getConfig();
            websiteConfigVo = JSON.parseObject(config, WebsiteConfigVo.class);
            redisService.set(WEBSITE_CONFIG, config);
        }
        return websiteConfigVo;
    }

    @Override
    public String getAbout() {
        Object value = redisService.get(ABOUT);
        return Objects.nonNull(value) ? value.toString() : "";
    }

    @Override
    public void updateAbout(BlogInfoVo blogInfoVo) {
        redisService.set(ABOUT, blogInfoVo.getAboutContent());
    }

    @Override
    public WebConfigDTO getWebsiteConfigBlog() {
        //查询网站配置
        WebsiteConfigVo config = this.getWebsiteConfig();
        // 查询页面图片
        List<PageVo> pageVoList = pageService.listPages();
        // 查询文章数量
        Integer articleCount = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .eq(Article::getStatus, PUBLIC.getStatus())
                .eq(Article::getIsDelete, FALSE));
        // 查询分类数量
        Integer categoryCount = categoryDao.selectCount(null);
        // 查询标签数量
        Integer tagCount = tagDao.selectCount(null);
        //封装数据
        return WebConfigDTO.builder()
                .websiteConfig(config)
                .pageList(pageVoList)
                .articleCount(articleCount)
                .categoryCount(categoryCount)
                .tagCount(tagCount)
                .build();
    }
}

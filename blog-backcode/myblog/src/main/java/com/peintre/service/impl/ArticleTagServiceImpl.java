package com.peintre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.dao.ArticleTagDao;
import com.peintre.entity.ArticleTag;
import com.peintre.service.ArticleTagService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Program: MyBlog
 * @Description: (tb_article_tag)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-21 14:33:21
 **/
@Service("articleTagService")
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagDao, ArticleTag> implements ArticleTagService {
   
    @Autowired
    private ArticleTagDao articleTagDao;
}


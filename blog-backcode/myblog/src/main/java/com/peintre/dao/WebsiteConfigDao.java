package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import com.peintre.entity.WebsiteConfig;


/**
 * @Program: MyBlog
 * @Description: (tb_website_config)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-21 14:01:08
 **/
@Repository
public interface WebsiteConfigDao extends BaseMapper<WebsiteConfig> {

}


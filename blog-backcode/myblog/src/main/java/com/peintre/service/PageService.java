package com.peintre.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.entity.Page;
import com.peintre.vo.PageVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_page)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-22 10:44:25
 **/
public interface PageService extends IService<Page> {

    /**
     * 查询页面
     *
     * @return {@link List <PageVo>} 页面列表
     */
    List<PageVo> listPages();

    /**
     * 保存/更新页面
     *
     * @param pageVo 页面信息
     */
    void saveOrUpdatePage(PageVo pageVo);

    /**
     * 删除页面
     *
     * @param pageId 页面id
     */
    void deletePage(Integer pageId);
}


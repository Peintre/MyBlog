package com.peintre.service;


import cn.hutool.db.PageResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.peintre.dto.CategoryBackDTO;
import com.peintre.dto.CategoryOptionDTO;
import com.peintre.dto.blog.CategoryDTO;
import com.peintre.entity.Category;
import com.peintre.vo.CategoryVo;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;

import java.util.List;

/**
 * @Program: MyBlog
 * @Description: (tb_category)表服务接口
 * @Author: Peintre
 * @Date: 2022-12-18 17:16:49
 **/
public interface CategoryService extends IService<Category> {

    /**
     * 条件查询文章分类
     *
     * @param condition 条件
     * @return {@link List <CategoryOptionDTO>} 分类列表
     */
    List<CategoryOptionDTO> listCategoriesBySearch(ConditionVo condition);

    /**
     * (后台)条件查询分类
     *
     * @param conditionVo 条件
     * @return {@link PageResult<CategoryBackDTO>} 后台分类
     */
    PageResultVo<CategoryBackDTO> listBackCategories(ConditionVo conditionVo);

    /**
     * 添加/修改分类
     *
     * @param categoryVo 分类信息
     * @return void
     */
    void addOrUpdateCategory(CategoryVo categoryVo);

    /**
     * 删除分类
     *
     * @param idsVo 分类id集合
     */
    void deleteCategory(IdsVo idsVo);

    /**
     * (前台)查询分类
     *
     * @return 分类列表
     */
    PageResultVo<CategoryDTO> listCategories();
}


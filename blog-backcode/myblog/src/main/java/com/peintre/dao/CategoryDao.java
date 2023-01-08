package com.peintre.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.peintre.dto.CategoryBackDTO;
import com.peintre.dto.blog.CategoryDTO;
import com.peintre.vo.ConditionVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.peintre.entity.Category;

import java.util.List;


/**
 * @Program: MyBlog
 * @Description: (tb_category)表数据访问层
 * @Author: Peintre
 * @Date: 2022-12-18 18:29:14
 **/
@Repository
public interface CategoryDao extends BaseMapper<Category> {

    /**
     * 查询后台分类列表
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List<CategoryBackDTO>} 分类列表
     */
    List<CategoryBackDTO> listCategoryBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVo condition);

    /**
     * 查询分类和对应文章数量
     *
     * @return 分类列表
     */
    List<CategoryDTO> listCategoryDTO();
}


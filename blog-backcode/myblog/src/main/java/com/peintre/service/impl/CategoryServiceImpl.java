package com.peintre.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.peintre.config.BizException;
import com.peintre.dao.ArticleDao;
import com.peintre.dao.CategoryDao;
import com.peintre.dto.CategoryBackDTO;
import com.peintre.dto.CategoryOptionDTO;
import com.peintre.dto.blog.CategoryDTO;
import com.peintre.entity.Article;
import com.peintre.entity.Category;
import com.peintre.service.CategoryService;
import com.peintre.utils.BeanCopyUtils;
import com.peintre.utils.PageUtils;
import com.peintre.vo.CategoryVo;
import com.peintre.vo.ConditionVo;
import com.peintre.vo.IdsVo;
import com.peintre.vo.PageResultVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @Program: MyBlog
 * @Description: (tb_category)表服务实现类
 * @Author: Peintre
 * @Date: 2022-12-18 18:29:03
 **/
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, Category> implements CategoryService {
   
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ArticleDao articleDao;

    @Override
    public List<CategoryOptionDTO> listCategoriesBySearch(ConditionVo condition) {
        // 搜索分类
        List<Category> categoryList = categoryDao.selectList(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords())
                .orderByDesc(Category::getId));
        return BeanCopyUtils.copyList(categoryList, CategoryOptionDTO.class);
    }

    @Override
    public PageResultVo<CategoryBackDTO> listBackCategories(ConditionVo condition) {
        // 查询分类数量
        Integer count = categoryDao.selectCount(new LambdaQueryWrapper<Category>()
                .like(StringUtils.isNotBlank(condition.getKeywords()), Category::getCategoryName, condition.getKeywords()));
        if (count == 0) {
            return new PageResultVo<>();
        }
        // 分页查询分类列表
        List<CategoryBackDTO> categoryList = categoryDao.listCategoryBackDTO(PageUtils.getLimitCurrent(), PageUtils.getSize(), condition);
        return new PageResultVo<>(categoryList, count);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addOrUpdateCategory(CategoryVo categoryVo) {
        // 判断分类名重复
        Category existCategory = categoryDao.selectOne(new LambdaQueryWrapper<Category>()
                .select(Category::getId)
                .eq(Category::getCategoryName, categoryVo.getCategoryName()));
        if (Objects.nonNull(existCategory) && !existCategory.getId().equals(categoryVo.getId())) {
            throw new BizException("分类名已存在");
        }
        Category category = Category.builder()
                .id(categoryVo.getId())
                .categoryName(categoryVo.getCategoryName())
                .build();
        this.saveOrUpdate(category);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteCategory(IdsVo idsVo) {
        // 查询分类id下是否有文章
        Integer count = articleDao.selectCount(new LambdaQueryWrapper<Article>()
                .in(Article::getCategoryId, idsVo.getIdList()));
        if (count > 0) {
            throw new BizException("删除失败，该分类下存在文章");
        }
        categoryDao.deleteBatchIds(idsVo.getIdList());
    }

    @Override
    public PageResultVo<CategoryDTO> listCategories() {
        return new PageResultVo<>(categoryDao.listCategoryDTO(), categoryDao.selectCount(null));
    }
}

